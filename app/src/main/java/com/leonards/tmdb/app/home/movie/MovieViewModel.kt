package com.leonards.tmdb.app.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
) : ViewModel() {
    val moviesStream by lazy {
        movieUseCase.fetchMovies()
            .cachedIn(viewModelScope)
    }
    private val _uiState: MutableStateFlow<UiState<PagingData<Movie>>> = MutableStateFlow(UiState.Idle)
    val uiState = _uiState.asStateFlow()
    private var pagingJob: Job? = null

    init {
        _uiState.value = UiState.Loading
        loadMovies()
    }

    private fun loadMovies() {
        pagingJob?.cancel()
        _uiState.value = UiState.Loading
        pagingJob = viewModelScope.launch {
            movieUseCase.fetchMovies()
                .cachedIn(viewModelScope)
                .catch { throwable ->
                    _uiState.value = UiState.Error(throwable)
                }
                .collectLatest { pagingData ->
                    _uiState.value = UiState.LoadingDone
                    _uiState.value = UiState.Success(pagingData)
                }
        }
    }
}