package com.dicoding.tmdb.app.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.dicoding.tmdb.core.utils.ImageSize

class AppPreferences(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun getImageSize(): ImageSize {
        val imageSize = preferences.getString(image_size_key, String())
        return if (imageSize == null) {
            ImageSize.Medium
        } else {
            ImageSize.valueOf(imageSize)
        }
    }

    companion object {
        const val image_size_key = "image_size"
    }
}