package com.example.mengzhi.annotationTesting

import android.app.Activity
import android.util.Log
import android.view.View
import java.lang.Exception
import java.lang.reflect.Proxy
import java.util.concurrent.Callable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

object InjectUtils2 {
    const val TAG = "InjectUtils2"

    private fun getMethodWithAnnotation(
        members: Collection<KCallable<*>>,
        clazz: KClass<*>
    ): KCallable<*>? {
        for (member in members) {
            member.annotations.find { it.annotationClass == clazz }?.apply {
                return member
            }
        }
        return null
    }

    private inline fun <reified A : Annotation> getAnnotation(kClazz: KClass<*>): Annotation? {
        return kClazz.findAnnotation<A>()
    }

    fun injectEvent(activity: Activity) {
        val activityClass: KClass<out Activity> = activity::class
        val declaredMembers = activityClass.members

        Log.i(TAG, "====declaredMembers==$declaredMembers")
        Log.i(TAG, "===annotations===${activityClass.annotations}")
        val member = getMethodWithAnnotation(declaredMembers, OnClick::class) ?: return
        val eventTypeAnnotation = getAnnotation<EventType>(OnClick::class)?.let {
            it as EventType
        } ?: return

        // OnClickListener.class
        val listenerType: KClass<*> = eventTypeAnnotation.listenerType
        //setOnClickListener
        val listenerSetter: String = eventTypeAnnotation.listenerSetter

        val viewIds = member.annotations.find { it is OnClick }.let {
            (it as OnClick).value
        }

        val handler = ListenerInvocationHandler(activity, member)
        val listenerProxy = Proxy.newProxyInstance(
            listenerType.java.classLoader,
            arrayOf(listenerType.java),
            handler
        )

        try {
            // 遍历注解的值
            for (viewId in viewIds) {
                // 获得当前activity的view（赋值）
                val view = activity.findViewById<View>(viewId)
                // 获取指定的方法(不需要判断是Click还是LongClick)
                // 如获得：setOnClickLisnter方法，参数为OnClickListener
                // 获得 setOnLongClickLisnter，则参数为OnLongClickLisnter
                // 获取指定的方法(不需要判断是Click还是LongClick)
                // 如获得：setOnClickLisnter方法，参数为OnClickListener
                // 获得 setOnLongClickLisnter，则参数为OnLongClickLisnter
                val setter = view.javaClass.getMethod(listenerSetter, listenerType.java)
//                val setter = view::class.members.find { it.name == listenerSetter }
//                setter?.call(view, listenerProxy)
                // 执行方法
//                Log.i(TAG, setter.toString())
                setter?.invoke(view, listenerProxy) //执行setOnclickListener里面的回调 onclick方法
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}