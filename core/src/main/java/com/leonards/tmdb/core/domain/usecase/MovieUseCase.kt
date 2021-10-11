package com.leonards.tmdb.core.domain.usecase

import androidx.paging.PagingData
import com.leonards.tmdb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun fetchMovies(): Flow<PagingData<Movie>>
}