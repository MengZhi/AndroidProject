package com.example.mengzhi.proxyTesting

import android.util.Log

class BbFactory : WomanToolsFactory {
    override fun saleWomanTools(length: Float) {
        Log.i(Client.TAG, "按需求定制了一个高度为" + length + "的男model")
    }
}