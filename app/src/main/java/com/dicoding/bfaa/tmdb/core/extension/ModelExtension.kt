package com.dicoding.bfaa.tmdb.core.extension

import com.dicoding.bfaa.tmdb.core.data.source.remote.response.GenreResponse

fun mapGenreList(list: List<GenreResponse>): List<String> =
    list.map { genre -> mapGenre(genre) }

fun mapGenre(genre: GenreResponse): String = genre.name
