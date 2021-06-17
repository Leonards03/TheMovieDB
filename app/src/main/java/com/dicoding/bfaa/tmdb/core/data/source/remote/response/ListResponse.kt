package com.dicoding.bfaa.tmdb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    val page: Int,
    val results: List<T>,
    @SerializedName("total_pages")
    val totalPages: Int
)