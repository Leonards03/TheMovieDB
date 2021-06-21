package com.dicoding.tmdb.app.di

import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.domain.usecase.FavoriteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideFavoriteUseCase(): FavoriteUseCase
    fun provideAppPreferences(): AppPreferences
}