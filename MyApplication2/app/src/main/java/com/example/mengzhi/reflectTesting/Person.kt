package com.example.mengzhi.reflectTesting

class Person {
    var personName: String = ""
    private var age = 0

    constructor(){}

    //包含一个带参的构造器和一个不带参的构造器
    constructor(name: String, age: Int) : super() {
        this.personName = name
        this.age = age
    }

    fun getName(): String {
        println("this is getName()!")
        return personName
    }

    fun setName(name: String) {
        this.personName = name
        println("this is setName()!")
    }

    fun getAge(): Int {
        println("this is getAge()!")
        return age
    }

    fun setAge(age: Int) {
        this.age = age
        println("this is setAge()!")
    }

    //私有方法
    private fun privateMethod() {
        println("this is private method!")
    }

    companion object {
        const val TAG = "TestReflection"
    }
}