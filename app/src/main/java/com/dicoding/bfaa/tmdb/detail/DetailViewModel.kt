package com.dicoding.bfaa.tmdb.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.usecase.DetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
) : ViewModel() {
    private val id: MutableStateFlow<Int> = MutableStateFlow(NO_ID)
    private val _itemIsFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(true)

    var itemType: MutableStateFlow<ItemType> = MutableStateFlow(ItemType.Movie)

    val itemIsFavorite get() = _itemIsFavorite.asLiveData()

    val isLoading get() = _isLoading.asLiveData()

    init {
        handle.get<Int>(EXTRA_ID)?.let {
            id.value = it
        }
        handle.get<ItemType>(EXTRA_TYPE)?.let {
            itemType.value = it
        }
    }

    val movieDetails by lazy { detailUseCase.getMovieDetails(id.value).asLiveData() }

    val tvShowDetails by lazy { detailUseCase.getTvShowDetails(id.value).asLiveData() }

    fun setFavorite(movie: Movie) = detailUseCase.setFavorite(movie, _itemIsFavorite.value)

    fun setFavorite(tvShow: TvShow) = detailUseCase.setFavorite(tvShow, _itemIsFavorite.value)

    fun setFavoriteState(state: Boolean) {
        _itemIsFavorite.value = state
    }

    fun toggleFavoriteState() {
        _itemIsFavorite.value = !_itemIsFavorite.value
    }

    fun setLoadingState(state: Boolean) {
        _isLoading.value = state
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ID = "extra_item_id"
        const val NO_ID = -1
    }
}