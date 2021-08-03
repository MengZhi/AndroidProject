package com.example.mengzhi.myapplication2

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class JniTestingActivity: Activity() {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    val A = 100

    var name = "Derry" // 签名：Ljava/lang/String;


    var age = 29 // 签名：I


    // Java 本地方法  实现：native层
    external fun getStringPwd(): String?
    external fun getStringPwd2(): String?

    // -------------  交互操作 JNI
//    external fun changeName()
//    external fun changeAge()
//    external fun callAddMethod()


    // 专门写一个函数，给native成调用
    fun add(number1: Int, number2: Int): Int {
        return number1 + number2 + 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jni_activity_main)

        // Example of a call to a native method
        val tv: TextView = findViewById(R.id.sample_text)
//        changeName()
        tv.text = name
//        changeAge()
        tv.text = "" + age
//        callAddMethod()
    }
}