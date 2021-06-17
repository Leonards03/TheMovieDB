package com.dicoding.bfaa.tmdb.core.domain.usecase

import androidx.paging.PagingData
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun fetchMovies(): Flow<PagingData<Movie>>
}