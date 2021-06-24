package com.dicoding.tmdb.favorite.di

import android.content.Context
import com.dicoding.tmdb.app.di.FavoriteModuleDependencies
import com.dicoding.tmdb.core.domain.model.DomainModel
import com.dicoding.tmdb.favorite.presentation.fragment.BaseFavoriteFragment
import com.dicoding.tmdb.favorite.ui.movie.MovieFragment
import com.dicoding.tmdb.favorite.ui.tvshow.TvShowFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(fragment: MovieFragment)
    fun inject(fragment: TvShowFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
