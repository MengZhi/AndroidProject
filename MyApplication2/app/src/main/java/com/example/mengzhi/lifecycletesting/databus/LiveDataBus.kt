package com.example.mengzhi.lifecycletesting.databus

import androidx.lifecycle.MutableLiveData
import java.util.HashMap

class LiveDataBus private constructor() {
    //存放订阅者
    private var bus: MutableMap<String, MutableLiveData<Any>>? = null

//    private val liveDataBus: LiveDataBus = LiveDataBus()

    init {
        bus = HashMap()
    }

    fun getInstance(): LiveDataBus? {
        return liveDataBus
    }

    //注册订阅者，（存入map）
    @Synchronized
    fun <T> with(key: String, type: Class<T>?): MutableLiveData<T>? {
        if (!bus!!.containsKey(key)) {
            bus!![key] = MutableLiveData()
        }
        return bus!![key] as MutableLiveData<T>?
    }

    companion object {
        val liveDataBus: LiveDataBus = LiveDataBus()
    }
}