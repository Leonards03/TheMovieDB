package com.dicoding.tmdb.app.home.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.tmdb.app.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}