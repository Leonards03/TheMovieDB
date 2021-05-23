package com.dicoding.bfaa.tmdb.core.data.source.remote.response

data class ListResponse<T>(
    val page: Int,
    val results: List<T>,
)