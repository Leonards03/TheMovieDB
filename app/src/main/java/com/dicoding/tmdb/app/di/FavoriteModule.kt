package com.dicoding.tmdb.app.di

import com.dicoding.tmdb.core.domain.interactor.FavoriteInteractor
import com.dicoding.tmdb.core.domain.usecase.FavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {
    @Binds
    abstract fun bindsFavoriteUseCase(favoriteInteractor: FavoriteInteractor): FavoriteUseCase
}