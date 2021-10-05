package com.example.mengzhi.proxyTesting

import com.example.mengzhi.proxyTesting.dynamicProxy.MarkCompany
import com.example.mengzhi.proxyTesting.dynamicProxy.ProxyUtils
import java.lang.reflect.Method


object Client {
    const val TAG = "ProxyTestClient"
    @JvmStatic
    fun test() {
        /*静态代理模式---------------*/
//        ManToolsFactory factory = new AaFactory();
//        Mark mark = new Mark(factory);
//        mark.saleManTools("D");
//
//        WomanToolsFactory womanToolsFactory = new BbFactory();
//        Alvin av = new Alvin(womanToolsFactory);
//        av.saleWomanTools(1.8f);

        /*动态代理模式---------------*/
        val markCompany = MarkCompany()

        //张三来了
        val aafactory: ManToolsFactory = AaFactory()
        markCompany.factory = aafactory
        val employee1 = markCompany.proxyInstance as ManToolsFactory
        employee1.saleManTools("E")

        //张三老婆来了
        val bbToolsFactory: WomanToolsFactory = BbFactory()
        markCompany.factory = bbToolsFactory
        val employee2 = markCompany.proxyInstance as WomanToolsFactory
        employee2.saleWomanTools(1.9f)
//        ProxyUtils.generateClassFile(
//            aafactory.javaClass,
//            employee1.javaClass.simpleName
//        )
//        ProxyUtils.generateClassFile(
//            bbToolsFactory.javaClass,
//            employee2.javaClass.simpleName
//        )
//        val methods: Array<Method> = aafactory.javaClass.methods
//        for (method in methods) {
//            System.out.println(method.getName()) //打印方法名称
//        }
    }
}
