package com.leonards.tmdb.app.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonards.tmdb.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    movieUseCase: MovieUseCase,
) : ViewModel() {
    val moviesStream by lazy {
        movieUseCase.fetchMovies()
            .cachedIn(viewModelScope)
    }
}