package com.dicoding.bfaa.tmdb.home.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    val favoriteTvShows = favoriteUseCase.getFavoriteTvShows()
        .asLiveData()
}