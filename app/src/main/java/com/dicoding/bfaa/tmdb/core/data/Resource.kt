package com.dicoding.bfaa.tmdb.core.data



sealed class Resource<out R> {
    data class Error(val message: String) : Resource<Nothing>()
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T, val source: String = "Network") : Resource<T>()
}
