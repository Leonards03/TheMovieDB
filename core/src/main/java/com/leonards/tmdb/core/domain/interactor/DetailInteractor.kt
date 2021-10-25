package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.domain.repository.Repository
import com.leonards.tmdb.core.domain.usecase.*
import javax.inject.Inject


data class DetailInteractor @Inject constructor(
    override val getMovieDetails: GetMovieDetails,
    override val getTvShowDetails: GetTvShowDetails,
    override val setFavoriteMovie: SetFavoriteMovie,
    override val setFavoriteTvShow: SetFavoriteTvShow
) : DetailUseCase

interface DetailUseCase {
    val getMovieDetails: GetMovieDetails
    val getTvShowDetails: GetTvShowDetails
    val setFavoriteMovie: SetFavoriteMovie
    val setFavoriteTvShow: SetFavoriteTvShow
}