package com.example.mengzhi.myapplication2

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myarouter_annotations.MyARouter

@MyARouter(path = "/app/JniTestingActivity")
class JniTestingActivity: Activity() {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        val age = 29 // 签名：I

        @JvmStatic
        external fun changeAge()
    }

    private lateinit var textView: TextView
    private lateinit var ageTextView: TextView
    val A = 100
    var name = "Derry" // 签名：Ljava/lang/String;
    private val number = 1000.0


    // Java 本地方法  实现：native层
//    external fun getStringPwd(): String?
//    external fun getStringPwd2(): String?

    // -------------  交互操作 JNI
    external fun changeName()
    external fun callAddMethod()
    external fun changeNumber()

//     专门写一个函数，给native成调用
    fun add(number1: Int, number2: Int): Int {
        return number1 + number2 + 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jni_activity_main)
        textView = findViewById(R.id.sample_text)
        textView.text = name
        findViewById<TextView>(R.id.age_text_view).text = Companion.age.toString()
        ageTextView = findViewById<TextView>(R.id.age_text_view)
        ageTextView.text = "age: " + Companion.age.toString() + " number: " + number

        changeNameBtn()
        changeAgeBtn()
        callAddMethod()
    }

    private fun changeNameBtn() {
        findViewById<Button>(R.id.btn_change_name).setOnClickListener {
            changeName()
            textView.text = name
        }
    }

    private fun changeAgeBtn() {
        findViewById<Button>(R.id.btn_change_age).setOnClickListener {
            changeAge()
            changeNumber()
            ageTextView.text = "age: " + Companion.age.toString() + " number: " + number
        }
    }
}