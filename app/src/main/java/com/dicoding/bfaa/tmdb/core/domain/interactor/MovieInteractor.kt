package com.dicoding.bfaa.tmdb.core.domain.interactor

import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import com.dicoding.bfaa.tmdb.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val repository: Repository,
) : MovieUseCase {
    override fun getDiscoverMovieList() = repository.fetchDiscoverMovieList()

    override fun getNowPlayingMovies() = repository.fetchNowPlayingMovies()
    override fun getTopRatedMovie() = repository.fetchTopRatedMovie()
    override fun getPopularMovies() = repository.fetchPopularMovies()
    override fun getUpcomingMovies() = repository.fetchUpcomingMovies()
}