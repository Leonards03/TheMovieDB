package com.dicoding.bfaa.tmdb.core.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dicoding.bfaa.tmdb.core.data.Resource
import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.presentation.MoviePagingSource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun fetchDiscoverMovieList(): Flow<Resource<List<Movie>>>
    fun fetchDiscoverTvList(): Flow<Resource<List<TvShow>>>

    fun fetchUpcomingMovies(): Flow<Resource<List<Movie>>>
    fun fetchNowPlayingMovies(): Flow<Resource<List<Movie>>>
    fun fetchTopRatedMovie(): Flow<Resource<List<Movie>>>
    fun fetchPopularMovies(): Flow<Resource<List<Movie>>>

    fun fetchAiringToday(): Flow<Resource<List<TvShow>>>
    fun fetchLatestTvShow(): Flow<Resource<List<TvShow>>>
    fun fetchPopularTvShow(): Flow<Resource<List<TvShow>>>

    fun fetchMovie(id: Int): Flow<Resource<Movie>>
    fun fetchTvShow(id: Int): Flow<Resource<TvShow>>
    fun setFavorite(movie: Movie, itemIsFavorite: Boolean)
    fun getFavoriteMovies(): Flow<List<Movie>>
}