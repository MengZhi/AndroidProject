package com.example.mengzhi.reflectTesting

import android.util.Log
import java.lang.Exception
import java.lang.reflect.Method

class TestMethod {
    @Throws(Exception::class)
    fun testMethod() {
        Log.i(Person.TAG, "TestMethod")

        val clazz = Class.forName("com.example.mengzhi.reflectTesting.Person")
        Log.i(Person.TAG, "获取clazz对应类中的所有方法,不能获取private方法,且获取从父类继承来的所有方法")
        var methods: Array<Method> = clazz.methods
        for (method in methods) {
            Log.i(Person.TAG, method.name + "()")
        }

        Log.i(Person.TAG, "---------------------------")
        Log.i(Person.TAG, "获取所有方法，包括私有方法,所有声明的方法，都可以获取到，且只获取当前类的方法")
        methods = clazz.declaredMethods
        for (method in methods) {
            Log.i(Person.TAG, " " + method.name.toString() + "()")
        }

        println("---------------------------")
        Log.i(Person.TAG, "获取指定的方法，需要参数名称和参数列表，无参则不需要写")
        //  方法public void setName(String name) {  }
        var method: Method = clazz.getDeclaredMethod("setName", String::class.java)
        Log.i(Person.TAG, method.toString())
        Log.i(Person.TAG, "---")

        //  方法public void setAge(int age) {  }
        /* 这样写是获取不到的，如果方法的参数类型是int型
        如果方法用于反射，那么要么int类型写成Integer： public void setAge(Integer age) {  }
        要么获取方法的参数写成int.class*/method =
            clazz.getDeclaredMethod("setAge", Int::class.javaPrimitiveType)
        Log.i(Person.TAG, method.toString())
        Log.i(Person.TAG, "---------------------------")
        Log.i(
            Person.TAG, "执行方法，第一个参数表示执行哪个对象的方法剩下的参数是执行方法时需要传入的参数")
        val obje = clazz.newInstance()
        method.invoke(obje, 18)

        // 私有方法的执行，必须在调用invoke之前加上一句method.setAccessible（true）;
        method = clazz.getDeclaredMethod("privateMethod")
        Log.i(Person.TAG, method.toString())
        Log.i(Person.TAG, "---------------------------")
        Log.i(Person.TAG, "执行私有方法")
        method.isAccessible = true
        method.invoke(obje)
    }
}