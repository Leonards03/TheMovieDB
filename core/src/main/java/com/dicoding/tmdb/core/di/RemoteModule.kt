package com.dicoding.tmdb.core.di

import com.dicoding.made.core.BuildConfig.*
import com.dicoding.tmdb.core.data.source.remote.network.RemoteInterceptor
import com.dicoding.tmdb.core.data.source.remote.network.TMDBServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add(HOSTNAME, CERTIFICATE_1)
            .add(HOSTNAME, CERTIFICATE_2)
            .add(HOSTNAME, CERTIFICATE_3)
            .add(HOSTNAME, CERTIFICATE_4)
            .build()

    @Provides
    fun provideOkHttpClient(certificatePinner: CertificatePinner): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RemoteInterceptor())
            .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .certificatePinner(certificatePinner)
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