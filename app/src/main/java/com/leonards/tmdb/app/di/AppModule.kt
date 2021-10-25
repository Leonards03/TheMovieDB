package com.leonards.tmdb.app.di

import com.leonards.tmdb.core.domain.interactor.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsTvShowUseCase(tvShowInteractor: TvShowInteractor): TvShowUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsDetailUseCase(detailInteractor: DetailInteractor): DetailUseCase
}