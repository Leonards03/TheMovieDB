package com.dicoding.bfaa.tmdb.core.domain.model

data class TvShow(
    val id: Int,
    val title: String,
    val backdrop: String,
    val poster: String,
    val overview: String,
    val genres: String,
    val voteAverage: Double,
    val voteCount: Int,
    val numberOfEpisodes: Int,
    val firstAirDate: String,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val status: String,
)