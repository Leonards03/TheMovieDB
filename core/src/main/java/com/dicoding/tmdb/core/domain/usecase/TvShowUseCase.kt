package com.dicoding.tmdb.core.domain.usecase

import androidx.paging.PagingData
import com.dicoding.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowUseCase {
    fun fetchTvShows(): Flow<PagingData<TvShow>>
}