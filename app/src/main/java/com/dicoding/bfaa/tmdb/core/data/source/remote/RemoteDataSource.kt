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
    fun getNowPlayingMovies() = object : ResponseFlow<List<MovieResponse>>() {
        override suspend fun responseCall(): List<MovieResponse> =
            retrofit.getNowPlayingMovies().results
    }.asFlow()

    fun getDiscoverMovieList() = object : ResponseFlow<List<MovieResponse>>() {
        override suspend fun responseCall(): List<MovieResponse> =
            retrofit.getMovies().results
    }.asFlow()

    fun getPopularMovies() = object : ResponseFlow<List<MovieResponse>>() {
        override suspend fun responseCall(): List<MovieResponse> =
            retrofit.getPopularMovies().results
    }.asFlow()

    fun getUpcomingMovies() = object : ResponseFlow<List<MovieResponse>>() {
        override suspend fun responseCall(): List<MovieResponse> =
            retrofit.getUpcomingMovies().results
    }.asFlow()

    fun getTopRatedMovies() = object: ResponseFlow<List<MovieResponse>>() {
        override suspend fun responseCall(): List<MovieResponse> =
            retrofit.getTopRatedMovies().results
    }.asFlow()

    fun getMovie(id: Int) = object : ResponseFlow<MovieResponse>() {
        override suspend fun responseCall(): MovieResponse =
            retrofit.getMovie(id)
    }.asFlow()

    fun getDiscoverTvList() = object : ResponseFlow<List<TvShowResponse>>() {
        override suspend fun responseCall(): List<TvShowResponse> =
            retrofit.getTvShows().results
    }.asFlow()

    fun getAiringToday() = object : ResponseFlow<List<TvShowResponse>>() {
        override suspend fun responseCall(): List<TvShowResponse> =
            retrofit.getAiringToday().results
    }.asFlow()

    fun getLatestTvShow() = object : ResponseFlow<List<TvShowResponse>>() {
        override suspend fun responseCall(): List<TvShowResponse> =
            retrofit.getLatestTvShow().results
    }.asFlow()

    fun getPopularTvShow() = object : ResponseFlow<List<TvShowResponse>>() {
        override suspend fun responseCall(): List<TvShowResponse> =
            retrofit.getPopularTvShows().results
    }.asFlow()

    fun getTvShow(id: Int) = object : ResponseFlow<TvShowResponse>() {
        override suspend fun responseCall(): TvShowResponse =
            retrofit.getTvShow(id)
    }.asFlow()
}