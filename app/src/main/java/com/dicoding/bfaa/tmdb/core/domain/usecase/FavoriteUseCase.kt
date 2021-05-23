package com.dicoding.bfaa.tmdb.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dicoding.bfaa.tmdb.core.data.Resource
import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.presentation.MoviePagingSource
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<Movie>>
}