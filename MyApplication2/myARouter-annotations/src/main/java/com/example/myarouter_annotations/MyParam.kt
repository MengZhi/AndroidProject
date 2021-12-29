package com.example.myarouter_annotations

@Target(AnnotationTarget.FIELD) // 该注解作用在类之上 字段上有作用
@Retention(AnnotationRetention.BINARY) // 要在编译时进行一些预处理操作，注解会在class文件中存在
annotation class MyParam(val name: String = "")
