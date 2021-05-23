package com.dicoding.bfaa.tmdb.core.data.mapper

import com.dicoding.bfaa.tmdb.core.data.source.local.entity.TvShowEntity
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.TvShowResponse
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow


fun TvShowResponse.mapToDomain(): TvShow =
    TvShow(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = mapGenreList(genres),
        voteAverage = voteAverage,
        voteCount = voteCount,
        numberOfEpisodes = numberOfEpisodes,
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status),
        popularity = popularity
    )

fun List<TvShowResponse>.mapToDomain(): List<TvShow> =
    map { response ->
        response.mapToDomain()
    }

fun TvShowEntity.mapToDomain(): TvShow =
    TvShow(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = genres,
        voteAverage = voteAverage,
        voteCount = voteCount,
        numberOfEpisodes = numberOfEpisodes,
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status),
        popularity = popularity
    )

@JvmName("mapToDomainTvShowEntity")
fun List<TvShowEntity>.mapToDomain(): List<TvShow> =
    map { entity ->
        entity.mapToDomain()
    }

fun TvShow.mapToEntity(): TvShowEntity =
    TvShowEntity(
        id = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = genres,
        voteAverage = voteAverage.toDouble(),
        voteCount = voteCount,
        numberOfEpisodes = numberOfEpisodes,
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status),
        popularity = popularity.toDouble()
    )
