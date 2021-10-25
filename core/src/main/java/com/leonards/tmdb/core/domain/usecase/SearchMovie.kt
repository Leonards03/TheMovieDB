package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class SearchMovie @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(query: String) = repository.searchMovie(query)
}