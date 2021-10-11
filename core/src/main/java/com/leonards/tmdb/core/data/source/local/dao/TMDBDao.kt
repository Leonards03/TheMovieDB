package com.leonards.tmdb.core.data.source.local.dao

import androidx.room.*
import com.leonards.tmdb.core.data.source.local.entity.MovieEntity
import com.leonards.tmdb.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TMDBDao {
    @Query("SELECT * FROM movies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tv_shows")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getFavoriteMovie(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM tv_shows WHERE id = :id")
    fun getFavoriteTvShow(id: Int): Flow<TvShowEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(tvShow: TvShowEntity): Long

    @Delete
    suspend fun removeFromFavorite(movie: MovieEntity): Int

    @Delete
    suspend fun removeFromFavorite(tvShow: TvShowEntity): Int
}