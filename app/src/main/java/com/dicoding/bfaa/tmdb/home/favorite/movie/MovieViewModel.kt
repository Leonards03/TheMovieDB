package com.dicoding.bfaa.tmdb.home.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    val favoriteMovies = favoriteUseCase.getFavoriteMovies()
            .asLiveData()
}