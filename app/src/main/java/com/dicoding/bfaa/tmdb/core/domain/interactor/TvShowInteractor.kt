package com.dicoding.bfaa.tmdb.core.domain.interactor

import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import com.dicoding.bfaa.tmdb.core.domain.usecase.TvShowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowInteractor @Inject constructor(
    private val repository: Repository,
) : TvShowUseCase {
    override fun getDiscoverTvList() = repository.fetchDiscoverTvList()

    override fun getAiringToday(): Flow<Resource<List<TvShow>>> = repository.fetchAiringToday()

    override fun getLatestTvShow(): Flow<Resource<List<TvShow>>> = repository.fetchLatestTvShow()

    override fun getPopularTvShow(): Flow<Resource<List<TvShow>>> = repository.fetchPopularTvShow()

}