package com.example.mengzhi.proxyTesting.staticProxy

import com.example.mengzhi.proxyTesting.ManToolsFactory
import com.example.mengzhi.proxyTesting.WomanToolsFactory

class Mark(factory: ManToolsFactory) : ManToolsFactory, WomanToolsFactory {
    /*包含真实的对象*/
    var factory: ManToolsFactory

    /*前置处理器*/
    private fun doSthAfter() {
        println("精美包装，快递一条龙服务")
    }

    /*后置处理器*/
    private fun doSthBefore() {
        println("根据需求，进行市场调研和产品分析")
    }

    override fun saleManTools(size: String?) {
        doSthAfter()
        factory.saleManTools(size)
        doSthBefore()
    }

    override fun saleWomanTools(length: Float) {}

    init {
        this.factory = factory
    }
}