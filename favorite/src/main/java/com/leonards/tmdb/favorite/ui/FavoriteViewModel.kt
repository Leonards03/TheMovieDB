package com.leonards.tmdb.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leonards.tmdb.core.domain.usecase.FavoriteUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
): ViewModel() {
    val favoriteMovies = favoriteUseCase.getFavoriteMovies()
        .asLiveData()

    val favoriteTvShows = favoriteUseCase.getFavoriteTvShows()
        .asLiveData()
}
