package com.dicoding.bfaa.tmdb.core.domain.repository

import androidx.paging.PagingData
import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun fetchMovies(): Flow<PagingData<Movie>>
    fun fetchTvShows(): Flow<PagingData<TvShow>>

    fun fetchMovie(id: Int): Flow<Resource<Movie>>
    fun fetchTvShow(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun setFavorite(movie: Movie, itemIsFavorite: Boolean)
    fun setFavorite(tvShow: TvShow, itemIsFavorite: Boolean)
}