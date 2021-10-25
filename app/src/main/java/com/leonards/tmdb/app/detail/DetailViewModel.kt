package com.leonards.tmdb.app.detail

import androidx.lifecycle.*
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.app.utils.Event
import com.leonards.tmdb.core.data.states.ItemType
import com.leonards.tmdb.core.data.states.Resource
import com.leonards.tmdb.core.domain.interactor.DetailUseCase
import com.leonards.tmdb.core.domain.model.DomainModel
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
) : ViewModel() {
    private val id: MutableStateFlow<Int> = MutableStateFlow(NO_ID)
    private val itemType = MutableStateFlow(ItemType.Movie)
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackbarText

    private val _favoriteState = MutableStateFlow(false)
    val favoriteState: LiveData<Boolean>
        get() = _favoriteState.asLiveData()

    init {
        handle.get<Int>(EXTRA_ID)?.let {
            id.value = it
        }
        handle.get<ItemType>(EXTRA_TYPE)?.let {
            itemType.value = it
        }
        handleIntent()
    }

    val intent = Channel<DetailIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UiState<DomainModel>>(UiState.Idle)
    val state: StateFlow<UiState<DomainModel>>
        get() = _state

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is DetailIntent.FavoriteStateChanged -> favoriteStateChanged(intent.model)
                    DetailIntent.FetchDetails -> fetchDetails()
                }
            }
        }
    }

    private fun fetchDetails() {
        val loadResult = when (itemType.value) {
            ItemType.Movie -> detailUseCase.getMovieDetails(id.value)
            ItemType.TvShow -> detailUseCase.getTvShowDetails(id.value)
        }

        viewModelScope.launch {
            loadResult.collect { resource ->
                _state.value = when (resource) {
                    is Resource.Error -> UiState.Error(resource.exception)
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        _favoriteState.value = resource.dataFromDB()
                        UiState.Success(resource.data)
                    }
                }
            }
        }
    }

    private fun favoriteStateChanged(model: DomainModel) {
        val itemIsFavorite = _favoriteState.value
        _snackbarText.value = if (itemIsFavorite)
            Event(R.string.removed_from_my_list)
        else
            Event(R.string.added_to_my_list)
        when (model) {
            is Movie -> detailUseCase.setFavoriteMovie(model, itemIsFavorite)
            is TvShow -> detailUseCase.setFavoriteTvShow(model, itemIsFavorite)
        }
    }

    fun toggleFavoriteState(model: DomainModel) {
        _favoriteState.value = !_favoriteState.value
        viewModelScope.launch {
            intent.send(DetailIntent.FavoriteStateChanged(model))
        }
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ID = "extra_item_id"
        const val NO_ID = -1
    }
}