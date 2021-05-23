package com.dicoding.bfaa.tmdb.core.domain.usecase

import com.dicoding.bfaa.tmdb.core.data.Resource
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
   fun getDiscoverMovieList(): Flow<Resource<List<Movie>>>
   fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
   fun getTopRatedMovie(): Flow<Resource<List<Movie>>>
   fun getPopularMovies(): Flow<Resource<List<Movie>>>
   fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
}