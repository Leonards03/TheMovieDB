package com.leonards.tmdb.app.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.core.domain.interactor.TvShowUseCase
import com.leonards.tmdb.core.domain.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val tvShowUseCase: TvShowUseCase,
) : ViewModel() {
    val intent = Channel<TvShowIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UiState<PagingData<TvShow>>>(UiState.Idle)
    val state: StateFlow<UiState<PagingData<TvShow>>>
        get() = _state
    val query = MutableStateFlow(String())

    init {
        handleIntent()
        viewModelScope.launch {
            intent.send(TvShowIntent.FetchTvShows)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    TvShowIntent.FetchTvShows -> fetchTvShows()
                    TvShowIntent.SearchTvShows -> searchTvShows()
                }
            }
        }
    }

    private fun fetchTvShows() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            tvShowUseCase.fetchTvShows()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }
        }
    }

    private fun searchTvShows() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            query.flatMapLatest { tvShowUseCase.searchTvShows(it) }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }

        }
    }
}