package com.example.mengzhi.proxyTesting.dynamicProxy

//import sun.misc.ProxyGenerator
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


object ProxyUtils {
    fun generateClassFile(clazz: Class<*>, proxyName: String) {
        /*ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);*/
//        val proxyClassFile: ByteArray = ProxyGenerator.generateProxyClass(
//            proxyName, arrayOf(clazz)
//        )
//        val paths = clazz.getResource(".")!!.path
//        println(paths)
//        var out: FileOutputStream? = null
//        try {
//            out = FileOutputStream("$paths$proxyName.class")
//            out.write(proxyClassFile)
//            out.flush()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            try {
//                out?.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
    }
}