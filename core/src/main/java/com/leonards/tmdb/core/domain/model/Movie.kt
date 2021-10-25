package com.leonards.tmdb.core.domain.model

import com.leonards.tmdb.core.data.Constants
import com.leonards.tmdb.core.data.states.ItemType
import com.leonards.tmdb.core.utils.ImageSize

data class Movie(
    val movieId: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val genres: String,
    val overview: String,
    val voteAverage: Double,
    val runtime: Int?,
    val releaseDate: String,
    val director: String,
) : DomainModel(movieId) {
    fun getPosterUrl(imageSize: ImageSize) = Constants.getPosterUrl(poster, imageSize)
    fun getBackdropUrl(imageSize: ImageSize) = Constants.getBackdropUrl(backdrop, imageSize)
}
