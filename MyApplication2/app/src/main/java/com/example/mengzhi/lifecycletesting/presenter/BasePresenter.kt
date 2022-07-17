package com.example.mengzhi.lifecycletesting.presenter

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.mengzhi.lifecycletesting.view.IBaseView
import java.lang.ref.WeakReference

open class BasePresenter<T : IBaseView> : LifecycleObserver {
    var iGoodsView: WeakReference<T>? = null

    /**
     * 绑定view
     */
    fun attachView(view: T) {
        iGoodsView = WeakReference(view)
    }

    /**
     * 解绑
     */
    fun detachView() {
        if (iGoodsView != null) {
            iGoodsView!!.clear()
            iGoodsView = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreateX(owner: LifecycleOwner) {
        Log.i(TAG, "onCreateX")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStartX(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(owner: LifecycleOwner) {
        Log.i(TAG, "onResume ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestory(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny(owner: LifecycleOwner) {
    }

    companion object {
        private const val TAG = "BasePresenter"
    }
}