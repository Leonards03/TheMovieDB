package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.repository.Repository
import com.leonards.tmdb.core.domain.usecase.GetFavoriteMovies
import com.leonards.tmdb.core.domain.usecase.GetFavoriteTvShows
import javax.inject.Inject

data class FavoriteInteractor @Inject constructor(
    override val getFavoriteMovies: GetFavoriteMovies,
    override val getFavoriteTvShows: GetFavoriteTvShows
) : FavoriteUseCase

interface FavoriteUseCase {
    val getFavoriteMovies: GetFavoriteMovies
    val getFavoriteTvShows: GetFavoriteTvShows
}