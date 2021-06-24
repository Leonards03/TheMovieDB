package com.dicoding.tmdb.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.tmdb.core.data.source.local.dao.TMDBDao
import com.dicoding.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.tmdb.core.data.source.local.entity.TvShowEntity

@Database(
    entities = [
        MovieEntity::class,
        TvShowEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun tmdbDao(): TMDBDao
}