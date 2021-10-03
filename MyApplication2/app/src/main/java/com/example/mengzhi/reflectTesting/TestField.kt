package com.example.mengzhi.reflectTesting

import android.util.Log
import java.lang.Exception
import java.lang.reflect.Field

class TestField {
    @Throws(Exception::class)
    fun testField() {

        val className = "com.example.mengzhi.reflectTesting.Person"
        val clazz = Class.forName(className)
        Log.i(Person.TAG, "获取公用和私有的所有字段，但不能获取父类字段")
        val declaredFields: Array<Field> = clazz.declaredFields
        for (field in declaredFields) {
            Log.i(Person.TAG, field.name)
        }
        println("---------------------------")
        Log.i(Person.TAG, "获取指定字段")
        var field: Field = clazz.getDeclaredField("personName")
        Log.i(Person.TAG, field.name)
        val person = Person("ABC", 12)
        Log.i(Person.TAG, "获取指定字段的值")
        field.isAccessible = true
        val fields: Any = field.get(person)
        Log.i(Person.TAG, field.name.toString() + "=" + fields)
        println("设置指定对象指定字段的值")
        field.set(person, "DEF")
        Log.i(Person.TAG, field.name.toString() + "=" + person.getName())
        Log.i(
            Person.TAG,
            "字段是私有的，不管是读值还是写值，" +
                    "都必须先调用setAccessible（true）方法"
        )
        //     比如Person类中，字段name字段是非私有的，age是私有的
        field = clazz.getDeclaredField("age")
        field.isAccessible = true
        Log.i(Person.TAG, field.get(person).toString())
    }

}