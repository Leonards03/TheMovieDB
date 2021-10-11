package com.leonards.tmdb.app.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (throwable == null) {
                FirebaseCrashlytics.getInstance().log(message)
            } else {
                FirebaseCrashlytics.getInstance().recordException(throwable)
            }
        }
    }
}