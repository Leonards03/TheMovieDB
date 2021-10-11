package com.leonards.tmdb.core.extension

import com.leonards.tmdb.core.data.source.remote.response.GenreResponse

fun mapGenreList(list: List<GenreResponse>): List<String> =
    list.map { genre -> mapGenre(genre) }

fun mapGenre(genre: GenreResponse): String = genre.name
