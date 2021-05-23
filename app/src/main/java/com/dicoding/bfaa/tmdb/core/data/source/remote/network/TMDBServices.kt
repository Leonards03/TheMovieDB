package com.dicoding.bfaa.tmdb.core.data.source.remote.network

import com.dicoding.bfaa.tmdb.core.data.source.remote.response.ListResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBServices {
    @GET("search/movie")
    suspend fun searchMovie(query: String, page: Int)

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): ListResponse<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): ListResponse<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): ListResponse<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): ListResponse<MovieResponse>

    @GET("discover/movie")
    suspend fun getMovies(): ListResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        id: Int,
    ): MovieResponse


    @GET("search/tv")
    suspend fun searchTvShow(query: String, page: Int)

    @GET("tv/popular")
    suspend fun getPopularTvShows(): ListResponse<TvShowResponse>

    @GET("tv/airing_today")
    suspend fun getAiringToday(): ListResponse<TvShowResponse>

    @GET("tv/top_rated")
    suspend fun getLatestTvShow(): ListResponse<TvShowResponse>

    @GET("discover/tv")
    suspend fun getTvShows(): ListResponse<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id")
        id: Int,
    ): TvShowResponse
}