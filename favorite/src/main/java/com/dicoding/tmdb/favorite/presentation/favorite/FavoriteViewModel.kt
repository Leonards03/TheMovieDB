package com.dicoding.tmdb.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.tmdb.core.domain.usecase.FavoriteUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
): ViewModel() {
    val favoriteMovies = favoriteUseCase.getFavoriteMovies()
        .asLiveData()

    val favoriteTvShows = favoriteUseCase.getFavoriteTvShows()
        .asLiveData()
}
