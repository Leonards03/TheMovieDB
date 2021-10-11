package com.leonards.tmdb.core.domain.interactor

import com.leonards.tmdb.core.domain.repository.Repository
import com.leonards.tmdb.core.domain.usecase.FavoriteUseCase
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(
    private val repository: Repository,
) : FavoriteUseCase {
    override fun getFavoriteMovies() = repository.getFavoriteMovies()
    override fun getFavoriteTvShows() = repository.getFavoriteTvShows()
}