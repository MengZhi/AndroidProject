package com.example.mengzhi.reflectTesting

import android.util.Log
import java.lang.Exception
import java.lang.reflect.Constructor

class TestConstructor {
    @Throws(Exception::class)
    fun testConstructor() {
        Log.i(Person.TAG, "测试构造方法反射")
        val className = "com.example.mengzhi.reflectTesting.Person"
        val clazz = Class.forName(className) as Class<Person>
        println("获取全部Constructor对象-----")
        for (constructor in clazz.constructors) {
            Log.i(Person.TAG, constructor.toString())
        }
        println("获取某一个Constructor 对象，需要参数列表----")
        val constructor: Constructor<Person> = clazz.getConstructor(
            String::class.java,
            Int::class.javaPrimitiveType
        )
        Log.i(Person.TAG, constructor.toString())

        //2. 调用构造器的 newInstance() 方法创建对象
        println("调用构造器的 newInstance() 方法创建对象-----")
        val obj: Person = constructor.newInstance("Mark", 18)
        Log.i(Person.TAG, obj.getName())
    }
}