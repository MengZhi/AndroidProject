package com.example.mengzhi.mvvmTesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mengzhi.mvvmTesting.viewModel.APIViewModel
import com.example.mengzhi.myapplication2.R
import com.example.mengzhi.myapplication2.databinding.ActivityMvvmTestingBinding
import com.example.myarouter_annotations.MyARouter

@MyARouter(path = "/app/MvvmTestingActivity")
class MvvmTestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_testing)
        val binding = DataBindingUtil.setContentView<ActivityMvvmTestingBinding>(this, R.layout.activity_mvvm_testing)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(APIViewModel::class.java)

        binding.vm = viewModel

        viewModel.requestLogin("zhimeng", "123456")
    }
}