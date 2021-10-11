package com.leonards.tmdb.app.home.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.leonards.tmdb.app.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}