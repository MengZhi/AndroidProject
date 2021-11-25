package com.example.compiler

import com.example.myarouter_annotations.MyARouter
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import java.lang.reflect.Type
import java.sql.Types
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.example.myarouter_annotations.MyARouter")
@SupportedOptions("student")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
class TestProcessor : AbstractProcessor() {
    private lateinit var elementTool: Elements


    private lateinit var typesTool: Types

    private lateinit var messager: Messager

    private lateinit var filer: Filer

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementTool = processingEnv.elementUtils
        messager = processingEnv.messager
        filer = processingEnv.filer

        val values = processingEnv.options["student"]
        messager.printMessage(Diagnostic.Kind.NOTE, "$values>>>>>>>>>>>>>>>>>>>>>")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {

        messager.printMessage(Diagnostic.Kind.NOTE, "===========")

        val elements = roundEnv.getElementsAnnotatedWith(MyARouter::class.java)
        for (ele in elements) {
            val mainMethod = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(Array<System>::class.java, "args")
                .addStatement("\$T.out.println(\$S)", System::class.java, "Hello Java Pots")
                .build()

            val testClass = TypeSpec.classBuilder("DreamsTest")
                .addMethod(mainMethod)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build()

            val packagef = JavaFile.builder("com.example.mengzhi.annotation", testClass).build()

            try {
                packagef.writeTo(filer)
            } catch (e: IOException) {
                e.printStackTrace()
                messager.printMessage(Diagnostic.Kind.NOTE, "generate file fail" + e.message)
            }
        }
        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes()
    }
}