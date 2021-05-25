package com.dicoding.bfaa.tmdb.core.domain.usecase

import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowUseCase {
    fun getDiscoverTvList(): Flow<Resource<List<TvShow>>>
    fun getAiringToday(): Flow<Resource<List<TvShow>>>
    fun getLatestTvShow(): Flow<Resource<List<TvShow>>>
    fun getPopularTvShow(): Flow<Resource<List<TvShow>>>
}