package com.leonards.tmdb.app.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.leonards.tmdb.core.domain.interactor.TvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val tvShowUseCase: TvShowUseCase,
) : ViewModel() {
    val tvShowsStream by lazy {
        tvShowUseCase.fetchTvShows()
            .cachedIn(viewModelScope)
    }
}