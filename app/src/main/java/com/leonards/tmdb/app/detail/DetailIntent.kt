package com.leonards.tmdb.app.detail

import com.leonards.tmdb.core.domain.model.DomainModel

sealed class DetailIntent {
    object FetchDetails: DetailIntent()
    data class FavoriteStateChanged(val model: DomainModel): DetailIntent()
}