package com.example.mengzhi.lifecycletesting.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mengzhi.lifecycletesting.presenter.BasePresenter

abstract class BaseActivity<V : IBaseView, T : BasePresenter<V>> : AppCompatActivity() {
    protected var presenter: T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //由activity选择一个表示层
        presenter = createPresenter()
        presenter?.attachView(this as V)
        registerSDK()
        init()
    }

    protected abstract fun createPresenter(): T
    protected fun init() {}
    protected fun registerSDK() {}
    protected fun unRegisterSDK() {}
    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        unRegisterSDK()
    }
}