package com.example.mengzhi.annotationTesting

import android.view.View

@Target(
    AnnotationTarget.FUNCTION,
)
@Retention(
    AnnotationRetention.RUNTIME
)
@EventType(listenerType = View.OnClickListener::class, listenerSetter = "setOnClickListener")
annotation class OnClick(val value: IntArray)