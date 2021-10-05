package com.example.mengzhi.annotationTesting

import kotlin.reflect.KClass

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventType(val listenerType: KClass<*>, val listenerSetter: String)
