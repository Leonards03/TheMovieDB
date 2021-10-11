package com.leonards.tmdb.app.di

import com.leonards.tmdb.core.domain.interactor.DetailInteractor
import com.leonards.tmdb.core.domain.interactor.MovieInteractor
import com.leonards.tmdb.core.domain.interactor.SearchInteractor
import com.leonards.tmdb.core.domain.interactor.TvShowInteractor
import com.leonards.tmdb.core.domain.usecase.DetailUseCase
import com.leonards.tmdb.core.domain.usecase.MovieUseCase
import com.leonards.tmdb.core.domain.usecase.SearchUseCase
import com.leonards.tmdb.core.domain.usecase.TvShowUseCase
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

    @Binds
    @ViewModelScoped
    abstract fun bindsSearchUseCase(searchInteractor: SearchInteractor): SearchUseCase
}