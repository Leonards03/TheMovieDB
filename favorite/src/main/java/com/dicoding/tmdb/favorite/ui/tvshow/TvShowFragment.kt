package com.dicoding.tmdb.favorite.ui.tvshow

import android.content.Context
import com.dicoding.tmdb.app.di.FavoriteModuleDependencies
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.favorite.di.DaggerFavoriteComponent
import com.dicoding.tmdb.favorite.presentation.fragment.BaseFavoriteFragment
import dagger.hilt.android.EntryPointAccessors

class TvShowFragment: BaseFavoriteFragment<TvShow>() {
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
        viewModel.favoriteTvShows.observe(viewLifecycleOwner, ::render)
    }
}