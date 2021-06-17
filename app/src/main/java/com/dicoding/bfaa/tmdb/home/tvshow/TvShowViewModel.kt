package com.dicoding.bfaa.tmdb.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.bfaa.tmdb.core.domain.usecase.TvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    tvShowUseCase: TvShowUseCase,
) : ViewModel() {
    val tvShowsStream by lazy {
        tvShowUseCase.fetchTvShows()
            .cachedIn(viewModelScope)
    }
}