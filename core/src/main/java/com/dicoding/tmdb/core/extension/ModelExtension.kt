package com.dicoding.tmdb.core.extension

import com.dicoding.tmdb.core.data.source.remote.response.GenreResponse

fun mapGenreList(list: List<GenreResponse>): List<String> =
    list.map { genre -> mapGenre(genre) }

fun mapGenre(genre: GenreResponse): String = genre.name
