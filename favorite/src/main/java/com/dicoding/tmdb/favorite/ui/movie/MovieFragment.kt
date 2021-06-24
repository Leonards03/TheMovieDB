package com.dicoding.tmdb.favorite.ui.movie

import android.content.Context
import com.dicoding.tmdb.app.di.FavoriteModuleDependencies
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.favorite.di.DaggerFavoriteComponent
import com.dicoding.tmdb.favorite.presentation.fragment.BaseFavoriteFragment
import dagger.hilt.android.EntryPointAccessors

class MovieFragment : BaseFavoriteFragment<Movie>() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(EntryPointAccessors.fromApplication(
                context.applicationContext,
                FavoriteModuleDependencies::class.java
            ))
            .build()
            .inject(this)
    }

    override fun loadData() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner, ::render)
    }
}