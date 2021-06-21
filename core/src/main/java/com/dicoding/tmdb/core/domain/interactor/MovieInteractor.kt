package com.dicoding.tmdb.core.domain.interactor

import com.dicoding.tmdb.core.domain.repository.Repository
import com.dicoding.tmdb.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val repository: Repository,
) : MovieUseCase {
    override fun fetchMovies() = repository.fetchMovies()
}