package com.leonards.tmdb.app.home.tvshow

sealed class TvShowIntent {
    object FetchTvShows: TvShowIntent()
    object SearchTvShows: TvShowIntent()
}
