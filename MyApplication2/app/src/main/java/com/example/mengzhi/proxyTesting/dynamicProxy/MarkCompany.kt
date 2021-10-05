package com.example.mengzhi.proxyTesting.dynamicProxy

import android.util.Log
import com.example.mengzhi.proxyTesting.Client
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class MarkCompany : InvocationHandler {
    /*持有的真实对象*/
    var factory: Any? = null

    /*通过Proxy获得动态代理对象*/
    val proxyInstance: Any = Proxy.newProxyInstance(
            factory!!.javaClass.classLoader,
            factory!!.javaClass.interfaces, this)

    /*通过动态代理对象方法进行增强*/
    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        doSthBefore()
        val result = method.invoke(factory, *args)
        doSthAfter()
        return result
    }

    /*前置处理器*/
    private fun doSthAfter() {
        Log.i(Client.TAG, "精美包装，快递一条龙服务")
    }

    /*后置处理器*/
    private fun doSthBefore() {
        Log.i(Client.TAG, "根据需求，进行市场调研和产品分析")
    }
}