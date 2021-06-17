package com.dicoding.bfaa.tmdb.core.data

object Constants {
    // Network
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    const val appendToResponse = "keywords,credits"

    fun getImageBaseUrl(path: String) = "$IMAGE_BASE_URL/$path"

    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PAGE_INDEX = 1

    // Local
    const val DATABASE_NAME = "tmdb.db"
}