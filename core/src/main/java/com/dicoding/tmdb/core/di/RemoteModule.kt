package com.dicoding.tmdb.core.di

import com.dicoding.tmdb.core.data.Constants.BASE_URL
import com.dicoding.tmdb.core.data.source.remote.network.RemoteInterceptor
import com.dicoding.tmdb.core.data.source.remote.network.TMDBServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RemoteInterceptor())
            .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (com.dicoding.made.core.BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()

    @Provides
    fun provideTMDBService(okHttpClient: OkHttpClient): TMDBServices =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBServices::class.java)

}