package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.usecase.FetchMovies
import com.leonards.tmdb.core.domain.usecase.SearchMovie
import javax.inject.Inject

data class MovieInteractor @Inject constructor(
    override val fetchMovies: FetchMovies,
    override val searchMovie: SearchMovie,
) : MovieUseCase

interface MovieUseCase {
    val fetchMovies: FetchMovies
    val searchMovie: SearchMovie
}