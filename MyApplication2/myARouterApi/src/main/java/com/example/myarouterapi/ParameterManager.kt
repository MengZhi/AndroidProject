package com.example.myarouterapi

import android.app.Activity
import android.util.LruCache
import java.lang.Exception

class ParameterManager {
    // LRU缓存 key=类名      value=参数加载接口
    private var cache: LruCache<String, ParameterGet> = LruCache(100)

    // 使用者 只需要调用这一个方法，就可以进行参数的接收
    fun loadParameter(activity: Activity) { // 必须拿到 Personal_MainActivity
        val className = activity.javaClass.name // className == Personal_MainActivity
        var parameterLoad = cache[className] // 先从缓存里面拿 如果有  如果没有
        if (null == parameterLoad) { // 缓存里面没东东   提高性能
            // 拼接 如：Order_MainActivity + $$Parameter
            // 类加载Order_MainActivity + $$Parameter
            try {
                // 类加载Personal_MainActivity + $$Parameter
                val aClass = Class.forName(className + FILE_SUFFIX_NAME)
                // 用接口parameterLoad = 接口的实现类Personal_MainActivity
                parameterLoad = aClass.newInstance() as ParameterGet
                cache.put(className, parameterLoad) // 保存到缓存
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        parameterLoad.getParameter(activity) // 最终的执行  会执行我们生成的类
    }

    companion object {
        // 为什么还要拼接，此次拼接 是为了寻找他
        private const val FILE_SUFFIX_NAME = "$\$Parameter" // 为了这个效果：Order_MainActivity + $$Parameter

        private var instance: ParameterManager? = null

        // private boolean isCallback;
        fun getInstance(): ParameterManager? {
            if (instance == null) {
                synchronized(ParameterManager::class.java) {
                    if (instance == null) {
                        instance = ParameterManager()
                    }
                }
            }
            return instance
        }
    }
}