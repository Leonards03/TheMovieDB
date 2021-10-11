package com.leonards.tmdb.features.favorite.ui.tvshow

import android.content.Context
import com.leonards.tmdb.app.di.FavoriteModuleDependencies
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.features.favorite.di.DaggerFavoriteComponent
import com.leonards.tmdb.features.favorite.presentation.fragment.BaseFavoriteFragment
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