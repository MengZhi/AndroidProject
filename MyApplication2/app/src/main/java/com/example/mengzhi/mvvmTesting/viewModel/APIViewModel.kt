package com.example.mengzhi.mvvmTesting.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mengzhi.mvvmTesting.repository.APIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class APIViewModel: ViewModel() {
    val userLiveData = MutableLiveData<String>()

    fun requestLogin(userName: String, pwd: String) {
        viewModelScope.launch {
            Log.i(TAG, "APIViewModel requestLogin")
            userLiveData.value = APIRepository().requestLogin(userName, pwd)
        }
    }

    companion object {
        private const val TAG = "APIViewModel"
    }
}