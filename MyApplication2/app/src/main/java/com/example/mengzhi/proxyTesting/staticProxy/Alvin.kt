package com.example.mengzhi.proxyTesting.staticProxy

import com.example.mengzhi.proxyTesting.WomanToolsFactory

class Alvin(womanToolsFactory: WomanToolsFactory) : WomanToolsFactory {
    private val womanToolsFactory: WomanToolsFactory

    override fun saleWomanTools(length: Float) {
        doSthBefore()
        womanToolsFactory.saleWomanTools(length)
        doSthAfter()
    }

    /*前置处理器*/
    private fun doSthAfter() {
        println("精美包装，快递一条龙服务")
    }

    /*后置处理器*/
    private fun doSthBefore() {
        println("根据需求，进行市场调研和产品分析")
    }

    init {
        this.womanToolsFactory = womanToolsFactory
    }
}