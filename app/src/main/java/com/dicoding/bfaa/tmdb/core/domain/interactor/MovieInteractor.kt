package com.dicoding.bfaa.tmdb.core.domain.interactor

import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import com.dicoding.bfaa.tmdb.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val repository: Repository,
) : MovieUseCase {
    override fun fetchMovies() = repository.fetchMovies()
}