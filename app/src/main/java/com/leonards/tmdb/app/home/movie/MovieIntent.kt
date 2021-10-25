package com.leonards.tmdb.app.home.movie

sealed class MovieIntent {
    object FetchMovie : MovieIntent()
    object SearchMovie : MovieIntent()
}