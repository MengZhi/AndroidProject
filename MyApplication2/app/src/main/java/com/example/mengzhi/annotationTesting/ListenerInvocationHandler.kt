package com.example.mengzhi.annotationTesting

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KCallable

internal class ListenerInvocationHandler<T>(private val target: T, private val method: KCallable<*>) :
    InvocationHandler {
    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        return this.method.call(target, *args)
    }
}