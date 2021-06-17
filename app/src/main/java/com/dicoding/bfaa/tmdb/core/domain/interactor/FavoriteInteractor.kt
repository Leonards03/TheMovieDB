package com.dicoding.bfaa.tmdb.core.domain.interactor

import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(
    private val repository: Repository,
) : FavoriteUseCase {
    override fun getFavoriteMovies() = repository.getFavoriteMovies()
    override fun getFavoriteTvShows() = repository.getFavoriteTvShows()
}