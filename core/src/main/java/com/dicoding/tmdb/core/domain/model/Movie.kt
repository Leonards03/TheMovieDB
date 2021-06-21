package com.dicoding.tmdb.core.domain.model

import com.dicoding.tmdb.core.data.Constants
import com.dicoding.tmdb.core.utils.ImageSize

data class Movie(
    val id: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val genres: String,
    val overview: String,
    val voteAverage: Double,
    val runtime: Int?,
    val releaseDate: String,
    val director: String,
) {
    fun getPosterUrl(imageSize: ImageSize) = Constants.getPosterUrl(poster, imageSize)
    fun getBackdropUrl(imageSize: ImageSize) = Constants.getBackdropUrl(backdrop, imageSize)
}
