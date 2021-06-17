package com.dicoding.bfaa.tmdb.core.domain.interactor

import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import com.dicoding.bfaa.tmdb.core.domain.usecase.TvShowUseCase
import javax.inject.Inject

class TvShowInteractor @Inject constructor(
    private val repository: Repository,
) : TvShowUseCase {
    override fun fetchTvShows() = repository.fetchTvShows()
}