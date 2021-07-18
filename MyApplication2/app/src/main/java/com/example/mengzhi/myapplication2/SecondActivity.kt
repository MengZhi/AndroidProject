package com.example.mengzhi.myapplication2

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ListView
import com.example.mengzhi.myapplication2.R.id.fab
import com.example.mengzhi.myapplication2.R.id.toolbar

import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        addPreferencesFromResource(R.xml.perf_screen_2)

        var listView:ListView = findViewById(android.R.id.list)
//        var first = listView.adapter.getView(0, null, listView)
//        first.measure(0, 0)
//        Log.d("zhimeng", "first height: " + first.measuredHeight)
        var sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
    }



}
