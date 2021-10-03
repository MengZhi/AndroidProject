package com.example.mengzhi.proxyTesting

import android.util.Log

class AaFactory : ManToolsFactory {
    override fun saleManTools(size: String?) {
        Log.i(Client.TAG, "按需求定制了一个size为" + size + "的女model")
    }
}