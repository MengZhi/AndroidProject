package com.example.mengzhi.myapplication2

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import java.util.prefs.PreferencesFactory
import android.support.v7.preference.PreferenceFragmentCompat;


class MyPreferenceFragment: PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_screen)
    }
}