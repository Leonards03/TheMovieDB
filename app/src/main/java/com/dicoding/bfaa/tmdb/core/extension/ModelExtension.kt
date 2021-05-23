package com.dicoding.bfaa.tmdb.core.extension

import com.dicoding.bfaa.tmdb.core.data.source.remote.response.GenreResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.domain.model.Movie

fun valueOrEmpty(value: String?) =
    value ?: String()

fun mapGenreList(list: List<GenreResponse>): List<String> =
    list.map { genre -> mapGenre(genre) }

fun mapGenre(genre: GenreResponse): String = genre.name
