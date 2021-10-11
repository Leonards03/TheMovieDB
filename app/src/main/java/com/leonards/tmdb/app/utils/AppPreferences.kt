package com.leonards.tmdb.app.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.leonards.tmdb.core.utils.ImageSize

class AppPreferences(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun getImageSize(): ImageSize {
        val imageSize = preferences.getString(image_size_key, null)
        return if (imageSize == null) {
            preferences.edit(commit = true) { putString(image_size_key, ImageSize.Medium.name) }
            ImageSize.Medium
        } else {
            ImageSize.valueOf(imageSize)
        }
    }

    companion object {
        const val image_size_key = "image_size"
    }
}