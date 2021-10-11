package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.repository.Repository
import com.leonards.tmdb.core.domain.usecase.TvShowUseCase
import javax.inject.Inject

class TvShowInteractor @Inject constructor(
    private val repository: Repository,
) : TvShowUseCase {
    override fun fetchTvShows() = repository.fetchTvShows()
}