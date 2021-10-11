package com.leonards.tmdb.favorite.di

import android.content.Context
import com.leonards.tmdb.app.di.FavoriteModuleDependencies
import com.leonards.tmdb.favorite.ui.movie.MovieFragment
import com.leonards.tmdb.favorite.ui.tvshow.TvShowFragment
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
