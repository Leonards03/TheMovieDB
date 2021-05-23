package com.dicoding.bfaa.tmdb.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.usecase.TvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    tvShowUseCase: TvShowUseCase,
) : ViewModel() {
    val discoverTv = tvShowUseCase.getDiscoverTvList().asLiveData()

    val airingToday = tvShowUseCase.getAiringToday().asLiveData()

    val latestTvShow = tvShowUseCase.getLatestTvShow().asLiveData()

    val popularTvShow = tvShowUseCase.getPopularTvShow().asLiveData()
}