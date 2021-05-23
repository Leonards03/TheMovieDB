package com.dicoding.bfaa.tmdb.core.data

object Constants {
    // Network
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

    fun getImageBaseUrl(path: String) = "$IMAGE_BASE_URL/$path"

    // Local
    const val DATABASE_NAME = "tmdb.db"
}