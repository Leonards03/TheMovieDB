package com.dicoding.bfaa.tmdb.core.domain.usecase

import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<Movie>>
}