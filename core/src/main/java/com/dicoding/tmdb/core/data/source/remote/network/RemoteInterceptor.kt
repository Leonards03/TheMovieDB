package com.dicoding.tmdb.core.data.source.remote.network

import com.dicoding.made.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RemoteInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = BuildConfig.TMDB_API_KEY
    }
}