package com.dicoding.tmdb.core.domain.model

import com.dicoding.tmdb.core.data.Constants
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.utils.ImageSize

data class TvShow(
    val tvShowId: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val overview: String,
    val genres: String,
    val voteAverage: Double,
    val firstAirDate: String,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val status: String,
): DomainModel(tvShowId, ItemType.TvShow) {
    fun getPosterUrl(imageSize: ImageSize) = Constants.getPosterUrl(poster, imageSize)
    fun getBackdropUrl(imageSize: ImageSize) = Constants.getBackdropUrl(backdrop, imageSize)
}