package com.example.mengzhi.myapplication2

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment
import java.util.prefs.PreferencesFactory

class MyFragment : PreferenceFragment {
    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_screen)
    }
}