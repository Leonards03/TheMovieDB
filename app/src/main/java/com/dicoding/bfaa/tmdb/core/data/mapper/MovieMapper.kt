package com.dicoding.bfaa.tmdb.core.data.mapper

import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.GenreResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
//
//fun Movie.mapToEntity(): MovieEntity =
//    MovieEntity(
//        id = id,
//        title = title,
//        backdrop = valueOrEmpty(backdrop),
//        poster = valueOrEmpty(poster),
//        overview = overview,
//        genres = genres,
//        voteAverage = voteAverage.toDouble(),
//        voteCount = voteCount,
//        runtime = runtime,
//        releaseDate = releaseDate
//    )

fun MovieResponse.mapToDomain(): Movie =
    Movie(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        genres = mapGenreList(genres),
        overview = overview,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        releaseDate = releaseDate
    )

fun List<MovieResponse>.mapToDomain() =
    map { response ->
        response.mapToDomain()
    }

fun Movie.mapToEntity() =
    MovieEntity(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = genres,
        voteAverage = voteAverage.toDouble(),
        voteCount = voteCount,
        runtime = runtime,
        releaseDate = releaseDate
    )

fun MovieEntity.mapToDomain() =
    Movie(
        id = id,
        title = title,
        backdrop = backdrop,
        poster = poster,
        genres = genres,
        overview = overview,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        releaseDate = releaseDate
    )

@JvmName("mapToDomainMovieEntity")
fun List<MovieEntity>.mapToDomain() =
    map { entity ->
        entity.mapToDomain()
    }

fun valueOrEmpty(value: String?) =
    value ?: String()

fun mapGenreList(genres: List<GenreResponse>?) =
    genres?.joinToString(",") ?: String()




