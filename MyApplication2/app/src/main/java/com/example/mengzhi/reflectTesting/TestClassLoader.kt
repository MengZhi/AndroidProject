package com.example.mengzhi.reflectTesting

import android.util.Log
import java.io.FileNotFoundException

class TestClassLoader {
    @Throws(ClassNotFoundException::class, FileNotFoundException::class)
    fun testClassLoader() {
        Log.i(Person.TAG, "测试类加载器")
        //1. 获取一个系统的类加载器(可以获取，当前这个类PeflectTest就是它加载的)
        var classLoader = ClassLoader.getSystemClassLoader()
        println(classLoader)
        Log.i(Person.TAG, classLoader.toString())


        //2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
        classLoader = classLoader.parent
        Log.i(Person.TAG, classLoader.toString())


        //3. 获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
        classLoader = classLoader.parent
        Log.i(Person.TAG, classLoader?.toString()?:"empty")


        //4. 测试当前类由哪个类加载器进行加载（系统类加载器）:
        classLoader = Class.forName("com.example.mengzhi.reflectTesting.TestClassLoader")
            .classLoader
        Log.i(Person.TAG, classLoader.toString())


        //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
        classLoader = Class.forName("java.lang.Object")
            .classLoader
        Log.i(Person.TAG, classLoader.toString())
    }
}