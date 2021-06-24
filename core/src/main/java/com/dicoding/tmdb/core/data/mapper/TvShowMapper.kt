package com.dicoding.tmdb.core.data.mapper

import com.dicoding.tmdb.core.data.source.local.entity.TvShowEntity
import com.dicoding.tmdb.core.data.source.remote.response.TvShowResponse
import com.dicoding.tmdb.core.domain.model.TvShow


fun TvShowResponse.mapToDomain(): TvShow =
    TvShow(
        tvShowId = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = mapGenreList(genres),
        voteAverage = voteAverage.toDouble(),
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status)
    )

fun List<TvShowResponse>.mapToDomain(): List<TvShow> =
    map { response ->
        response.mapToDomain()
    }

fun TvShowEntity.mapToDomain(): TvShow =
    TvShow(
        tvShowId = id,
        title = title,
        backdrop = valueOrEmpty(backdrop),
        poster = valueOrEmpty(poster),
        overview = overview,
        genres = genres,
        voteAverage = voteAverage,
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status)
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
        voteAverage = voteAverage,
        firstAirDate = valueOrEmpty(firstAirDate),
        lastAirDate = valueOrEmpty(lastAirDate),
        numberOfSeasons = numberOfSeasons,
        status = valueOrEmpty(status),
    )
