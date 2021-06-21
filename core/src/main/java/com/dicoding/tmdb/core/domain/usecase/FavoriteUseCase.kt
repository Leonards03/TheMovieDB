package com.dicoding.tmdb.core.domain.usecase

import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTvShows(): Flow<List<TvShow>>
}