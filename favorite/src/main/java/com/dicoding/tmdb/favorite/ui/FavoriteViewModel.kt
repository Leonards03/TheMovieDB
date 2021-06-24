package com.dicoding.tmdb.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.tmdb.core.domain.usecase.FavoriteUseCase
import com.dicoding.tmdb.favorite.databinding.FragmentFavoriteBinding
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
): ViewModel() {
    val favoriteMovies = favoriteUseCase.getFavoriteMovies()
        .asLiveData()

    val favoriteTvShows = favoriteUseCase.getFavoriteTvShows()
        .asLiveData()
}
