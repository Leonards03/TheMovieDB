package com.dicoding.tmdb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    val results: List<T>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)