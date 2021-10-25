package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class SetFavoriteMovie @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(movie: Movie, isFavorite: Boolean) = repository.setFavorite(movie, isFavorite)
}