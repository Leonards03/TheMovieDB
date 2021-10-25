package com.leonards.tmdb.app.di

import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.core.domain.interactor.FavoriteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideFavoriteUseCase(): FavoriteUseCase
    fun provideAppPreferences(): AppPreferences
}