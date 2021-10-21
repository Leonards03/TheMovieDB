package com.leonards.tmdb.app.home.movie

import com.leonards.tmdb.core.domain.model.Movie

sealed class MovieIntent {
    object fetchMovie : MovieIntent()
    object searchMovie: MovieIntent()
}