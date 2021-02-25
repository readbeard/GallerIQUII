package com.example.galleriquii.fragment

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.galleriquii.R

private const val NAME = "name"
private const val AUTHOR = "author"
private const val TITLE = "title"
private const val CREATION_DATE = "creationDate"

class GalleryImageInfoFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        arguments.let {
            preferenceManager.findPreference<EditTextPreference>(NAME)?.summary = it?.getString(NAME)
            preferenceManager.findPreference<EditTextPreference>(AUTHOR)?.summary = it?.getString(AUTHOR)
            preferenceManager.findPreference<EditTextPreference>(TITLE)?.summary = it?.getString(TITLE)
            preferenceManager.findPreference<EditTextPreference>(CREATION_DATE)?.summary = it?.getString(CREATION_DATE)
        }

    }

    companion object {
        fun newInstance(
            name: String? = "Unknown",
            authorFullName: String? = "Unknown",
            title: String? = "Unknown",
            creationDate: String? = "Unknown"
        ) =
            GalleryImageInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    putString(AUTHOR, authorFullName)
                    putString(TITLE, title)
                    putString(CREATION_DATE, creationDate)
                }
            }
    }
}