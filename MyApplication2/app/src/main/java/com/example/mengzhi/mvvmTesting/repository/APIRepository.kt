package com.example.mengzhi.mvvmTesting.repository

import android.util.Log
import kotlinx.coroutines.delay

class APIRepository {
    suspend fun requestLogin(userName: String, pwd: String): String {
        delay(2000)
        Log.i(TAG, "this is APIRepository requestLogin")
        return "login success."
    }

    companion object {
        private const val TAG = "APIRepository"
    }
}