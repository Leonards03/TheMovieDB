package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.repository.Repository
import com.leonards.tmdb.core.domain.usecase.SearchUseCase
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val repository: Repository,
) : SearchUseCase {
    override fun searchMovie(query: String) = repository.searchMovie(query)

    override fun searchTvShow(query: String) = repository.searchTvShow(query)
}