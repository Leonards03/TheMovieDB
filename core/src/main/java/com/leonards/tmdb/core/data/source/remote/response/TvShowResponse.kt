package com.leonards.tmdb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class TvShowResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("backdrop_path")
    val backdrop: String?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: List<GenreResponse>?,
    @SerializedName("vote_average")
    val voteAverage: Number,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("status")
    val status: String,
)