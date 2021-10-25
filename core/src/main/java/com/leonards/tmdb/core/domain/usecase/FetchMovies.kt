package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class FetchMovies @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.fetchMovies()
}