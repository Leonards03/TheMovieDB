package com.dicoding.tmdb.core.data.source.remote.network

import com.dicoding.made.core.BuildConfig.TMDB_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RemoteInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", TMDB_API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}