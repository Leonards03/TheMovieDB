package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class SetFavoriteTvShow @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(tvShow: TvShow, isFavorite: Boolean) = repository.setFavorite(tvShow, isFavorite)
}