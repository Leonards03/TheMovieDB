package com.dicoding.bfaa.tmdb.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val genres: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int?,
    val releaseDate: String,
    val director: String,
)
