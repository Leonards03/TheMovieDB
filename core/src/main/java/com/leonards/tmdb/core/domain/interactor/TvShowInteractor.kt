package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.usecase.FetchTvShows
import com.leonards.tmdb.core.domain.usecase.SearchTvShow
import javax.inject.Inject

data class TvShowInteractor @Inject constructor(
    override val fetchTvShows: FetchTvShows,
    override val searchTvShows: SearchTvShow,
) : TvShowUseCase

interface TvShowUseCase {
    val fetchTvShows: FetchTvShows
    val searchTvShows: SearchTvShow
}