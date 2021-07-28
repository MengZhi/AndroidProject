package com.example.mengzhi.myapplication2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.KeyEvent
import android.widget.Button
import android.widget.ListView
import android.widget.TextView


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
        initButton()
    }

    private fun initButton() {
        val button = findViewById<Button>(R.id.second_button)
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("result", "clicked second button")
            setResult(3, intent)

            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        intent.putExtra("result", "clicked back button")
        setResult(3, intent)

        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent()
            intent.putExtra("result", "clicked back button")
            setResult(3, intent)

            finish()
            true
        } else {
            super.onKeyDown(keyCode, event);
        }
    }

}
