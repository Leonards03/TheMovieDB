package com.dicoding.bfaa.tmdb.core.data.mapper

import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.CreditsResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.GenreResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.domain.model.Movie

fun MovieResponse.mapToDomain(): Movie =
    Movie(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        genres = mapGenreList(genres),
        overview = overview,
        voteAverage = voteAverage.toDouble(),
        voteCount = voteCount,
        runtime = runtime,
        releaseDate = releaseDate,
        director = getDirectors(credits)
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
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        releaseDate = releaseDate,
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
        releaseDate = releaseDate,
        director = String()
    )

@JvmName("mapToDomainMovieEntity")
fun List<MovieEntity>.mapToDomain() =
    map { entity ->
        entity.mapToDomain()
    }

fun valueOrEmpty(value: String?) =
    value ?: String()

fun mapGenreList(genres: List<GenreResponse>?) =
    genres?.map { it.name }
        ?.subList(0, if (genres.size > 1) 2 else genres.size)
        ?.joinToString(",") ?: String()

fun getDirectors(list: CreditsResponse?): String {
    val director = list?.crew?.firstOrNull { it.job == "Director" }

    return director?.name ?: String()
}



