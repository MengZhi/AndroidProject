package com.example.mengzhi.myapplication2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.mengzhi.annotationTesting.InjectUtils
import com.example.mengzhi.annotationTesting.OnClick
import com.example.mengzhi.iotesting.Course
import com.example.myarouter_annotations.MyARouter
import com.example.myarouterapi.ParameterManager
import com.example.myarouterapi.RouterManager

@MyARouter(path = "/app/MainActivity")
class MainActivity : Activity() {
    private lateinit var testParcelButton: Button
    private lateinit var showAnimActivityButton: Button

//    @MyParam
//    private lateinit var orderDrawable: OrderDrawable

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

        InjectUtils.injectEvent(this)

//        ParameterManager.getInstance()?.loadParameter(this)
    }

//    @OnClick([R.id.anim_btn])
    fun onClick(view: View): Boolean {
        getAnimBtnListener()
        return false
    }

    @OnClick([R.id.start_service_btn])
    fun startServiceOnClick(view: View) {
        Log.i(TAG, "click startServiceOnClick")
        val intent = Intent()
        intent.action = "com.eample.dream.SERVICE"
        intent.`package` = "com.example.mengzhi.myapplication2"
        startService(intent)
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
//            val intent = Intent()
//            intent.setClass(this, AnimTestActivity::class.java)
//            intent.putExtra("course", getCourse())
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
            RouterManager.getInstance()
                ?.build("/app/AnimTestActivity")
                ?.withString("name", "hello this is MainActivity")
                ?.navigation(this)
            finish()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}