package com.dicoding.tmdb.core.domain.usecase

import androidx.paging.PagingData
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    fun searchMovie(query: String): Flow<PagingData<Movie>>
    fun searchTvShow(query: String): Flow<PagingData<TvShow>>
}