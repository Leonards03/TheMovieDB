package com.dicoding.bfaa.tmdb.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    movieUseCase: MovieUseCase,
) : ViewModel() {
    val discoverMovie = movieUseCase.getDiscoverMovieList().asLiveData()

    val nowPlaying = movieUseCase.getNowPlayingMovies().asLiveData()

    val upcomingMovies = movieUseCase.getUpcomingMovies().asLiveData()

    val topRatedMovies = movieUseCase.getTopRatedMovie().asLiveData()

    val popularMovies = movieUseCase.getPopularMovies().asLiveData()
}