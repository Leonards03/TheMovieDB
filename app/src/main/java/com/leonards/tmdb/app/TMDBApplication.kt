package com.leonards.tmdb.app

import android.app.Application
import com.leonards.tmdb.core.BuildConfig
import com.leonards.tmdb.app.utils.ReleaseTree
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