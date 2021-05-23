package com.dicoding.bfaa.tmdb.core.data.mapper

import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.GenreResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.domain.model.Movie


class MovieMapper : ModelMapper<Movie, MovieEntity, MovieResponse> {
    override fun mapFromResponse(response: MovieResponse): Movie =
        Movie(
            id = response.id,
            title = response.title,
            backdrop = valueOrEmpty(response.backdrop),
            poster = valueOrEmpty(response.poster),
            genres = mapGenreList(response.genres),
            overview = response.overview,
            voteAverage = response.voteAverage,
            voteCount = response.voteCount,
            runtime = response.runtime,
            releaseDate = response.releaseDate
        )

    override fun mapFromResponses(responses: List<MovieResponse>): List<Movie> =
        responses.map { response -> mapFromResponse(response) }

    override fun mapFromEntity(entity: MovieEntity): Movie =
        Movie(
            id = entity.id,
            title = entity.title,
            backdrop = entity.backdrop,
            poster = entity.poster,
            genres = entity.genres,
            overview = entity.overview,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            runtime = entity.runtime,
            releaseDate = entity.releaseDate
        )

    override fun mapFromEntities(entities: List<MovieEntity>): List<Movie> =
        entities.map { entity -> mapFromEntity(entity) }

    override fun mapToEntity(model: Movie): MovieEntity =
        MovieEntity(
            id = model.id,
            title = model.title,
            backdrop = valueOrEmpty(model.backdrop),
            poster = valueOrEmpty(model.poster),
            overview = model.overview,
            genres = model.genres,
            voteAverage = model.voteAverage.toDouble(),
            voteCount = model.voteCount,
            runtime = model.runtime,
            releaseDate = model.releaseDate
        )


    fun valueOrEmpty(value: String?) =
        value ?: String()

    fun mapGenreList(genres: List<GenreResponse>?) =
        genres?.joinToString(",") ?: String()

}

