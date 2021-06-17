package com.dicoding.bfaa.tmdb.core.data.source.remote

import com.dicoding.bfaa.tmdb.core.data.source.remote.network.TMDBServices
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.ResponseFlow
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.TvShowResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val retrofit: TMDBServices,
) {
    suspend fun getMovies(page: Int) = retrofit.getMovies(page)

    suspend fun getTvShows(page: Int) = retrofit.getTvShows(page)

    fun getMovie(id: Int) = object : ResponseFlow<MovieResponse>() {
        override suspend fun responseCall(): MovieResponse =
            retrofit.getMovie(id)
    }.flow

    fun getTvShow(id: Int) = object : ResponseFlow<TvShowResponse>() {
        override suspend fun responseCall(): TvShowResponse =
            retrofit.getTvShow(id)
    }.flow
}