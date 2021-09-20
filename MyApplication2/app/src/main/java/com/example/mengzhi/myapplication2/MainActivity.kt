package com.example.mengzhi.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mengzhi.iotesting.Course

class MainActivity : Activity() {
    private lateinit var testParcelButton: Button
    private lateinit var showAnimActivityButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


    }

    override fun onResume() {
        testParcelButton = findViewById(R.id.parcel_btn)
        showAnimActivityButton = findViewById(R.id.anim_btn)
        showAnimActivityButton.setOnClickListener(getAnimBtnListener())
        testParcelButton.setOnClickListener {
//            getParcelBtnListener()
        }
        super.onResume()
    }

    private fun getCourse(): Course {
        val course = Course()
        course.name = "Computer Organization"
        course.score = 100
        course.studentList = arrayListOf("xiao minng", "li hua")
        return course
    }

    private fun getAnimBtnListener(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent()
            intent.setClass(this, AnimTestActivity::class.java)
            intent.putExtra("course", getCourse())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}