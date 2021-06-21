package com.dicoding.tmdb.core.data

import com.dicoding.tmdb.core.utils.ImageSize

object Constants {
    // Network
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val appendToResponse = "keywords,credits"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
    private val posterSizes = arrayOf(
        "w185",
        "w342",
        "w780",
        "original"
    )
    private val backdropSizes = arrayOf(
        "w300",
        "w780",
        "w1280",
        "original"
    )

    fun getPosterUrl(path: String, imageSize: ImageSize) =
        "$IMAGE_BASE_URL/${posterSizes[imageSize.ordinal]}/$path"

    fun getBackdropUrl(path: String, imageSize: ImageSize) =
        "$IMAGE_BASE_URL/${backdropSizes[imageSize.ordinal]}/$path"

    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PAGE_INDEX = 1

    // Local
    const val DATABASE_NAME = "tmdb.db"
}