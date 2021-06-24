package com.dicoding.tmdb.app

import android.app.Application
import com.dicoding.made.core.BuildConfig
import com.dicoding.tmdb.app.utils.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TMDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG || BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(ReleaseTree())
    }
}