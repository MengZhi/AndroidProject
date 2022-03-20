package com.example.myarouterapi

import android.content.Context
import android.os.Build
import android.os.Bundle

class BundleManager {
    // Intent传输  携带的值，保存到这里
    private var bundle = Bundle()

    fun getBundle(): Bundle {
        return bundle
    }

    // 对外界提供，可以携带参数的方法
    fun withString(key: String, value: String?): BundleManager {
        bundle.putString(key, value)
        return this // 链式调用效果 模仿开源框架
    }

    fun withBoolean(key: String, value: Boolean): BundleManager {
        bundle.putBoolean(key, value)
        return this
    }

    fun withInt(key: String, value: Int): BundleManager {
        bundle.putInt(key, value)
        return this
    }

    fun withBundle(bundle: Bundle): BundleManager {
        this.bundle = bundle
        return this
    }

    // Derry只写到了这里，同学们可以自己增加 ...

    // Derry只写到了这里，同学们可以自己增加 ...
    // 直接完成跳转
    fun navigation(context: Context): Any? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            RouterManager.getInstance()?.navigation(context, this)
        } else null
    }
}