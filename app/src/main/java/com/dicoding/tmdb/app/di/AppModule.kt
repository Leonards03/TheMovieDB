package com.dicoding.tmdb.app.di

import com.dicoding.tmdb.core.domain.interactor.DetailInteractor
import com.dicoding.tmdb.core.domain.interactor.MovieInteractor
import com.dicoding.tmdb.core.domain.interactor.SearchInteractor
import com.dicoding.tmdb.core.domain.interactor.TvShowInteractor
import com.dicoding.tmdb.core.domain.usecase.DetailUseCase
import com.dicoding.tmdb.core.domain.usecase.MovieUseCase
import com.dicoding.tmdb.core.domain.usecase.SearchUseCase
import com.dicoding.tmdb.core.domain.usecase.TvShowUseCase
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