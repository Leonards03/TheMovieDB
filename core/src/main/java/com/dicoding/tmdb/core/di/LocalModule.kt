package com.dicoding.tmdb.core.di

import android.app.Application
import androidx.room.Room
import com.dicoding.made.core.BuildConfig
import com.dicoding.tmdb.core.data.Constants.DATABASE_NAME
import com.dicoding.tmdb.core.data.source.local.TMDBDatabase
import com.dicoding.tmdb.core.data.source.local.dao.TMDBDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application): TMDBDatabase =
        Room
            .databaseBuilder(application, TMDBDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes(BuildConfig.PARAPHRASE.toCharArray())))
            .build()

    @Provides
    fun provideTMDBDao(database: TMDBDatabase): TMDBDao = database.tmdbDao()
}