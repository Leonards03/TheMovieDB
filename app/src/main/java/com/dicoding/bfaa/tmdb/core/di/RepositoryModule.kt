package com.dicoding.bfaa.tmdb.core.di

import com.dicoding.bfaa.tmdb.core.data.RepositoryImpl
import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}