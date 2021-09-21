package com.artushock.moviesearcher.view.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.artushock.moviesearcher.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}