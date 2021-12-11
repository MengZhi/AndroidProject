package com.example.compiler.utils

/**
 * 字符串、集合判空工具
 */
object ProcessorUtils {
    // 如果是空 true
    fun isEmpty(cs: CharSequence?): Boolean {
        return cs == null || cs.isEmpty()
    }

    fun isEmpty(coll: Collection<*>?): Boolean {
        return coll == null || coll.isEmpty()
    }

    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }
}