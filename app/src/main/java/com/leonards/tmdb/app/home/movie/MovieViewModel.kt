package com.leonards.tmdb.app.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.core.domain.interactor.MovieUseCase
import com.leonards.tmdb.core.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
) : ViewModel() {
    val movieIntent = Channel<MovieIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UiState<PagingData<Movie>>>(UiState.Idle)
    val state: StateFlow<UiState<PagingData<Movie>>>
        get() = _state.asStateFlow()
    val query = MutableStateFlow(String())
    private var previousIntent: MovieIntent? = null
    private fun handleIntent() {
        viewModelScope.launch {
            movieIntent.consumeAsFlow().collect { intent ->
                previousIntent?.let { }
                when (intent) {
                    MovieIntent.FetchMovie -> fetchMovie()
                    MovieIntent.SearchMovie -> searchMovie()
                }
            }
        }
    }

    init {
        handleIntent()
        viewModelScope.launch {
            movieIntent.send(MovieIntent.FetchMovie)
        }
    }

    private fun fetchMovie() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            movieUseCase.fetchMovies()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }
        }
    }

    private fun searchMovie() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            query.flatMapLatest { movieUseCase.searchMovie(it) }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }
        }
    }
}