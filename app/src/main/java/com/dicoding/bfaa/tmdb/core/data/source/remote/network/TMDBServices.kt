package com.dicoding.bfaa.tmdb.core.data.source.remote.network

import com.dicoding.bfaa.tmdb.core.data.Constants
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.ListResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBServices {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
    ): ListResponse<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("page")
        page: Int = 1,
    ): ListResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        id: Int,
        @Query("append_to_response")
        extra: String = Constants.appendToResponse,
    ): MovieResponse

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
    ): ListResponse<TvShowResponse>

    @GET("discover/tv")
    suspend fun getTvShows(
        @Query("page")
        page: Int = 1,
    ): ListResponse<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id")
        id: Int,
    ): TvShowResponse
}