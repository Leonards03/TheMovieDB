package com.dicoding.bfaa.tmdb.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "backdrop_path")
    val backdrop: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "number_of_episodes")
    val numberOfEpisodes: Int,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String,

    @ColumnInfo(name = "last_air_date")
    val lastAirDate: String,

    @ColumnInfo(name = "number_of_seasons")
    val numberOfSeasons: Int,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,
)