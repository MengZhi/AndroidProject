package com.example.compiler

import com.example.myarouter_annotations.MyARouter
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.example.myarouter_annotations.MyARouter")
class ARouterProcessor : AbstractProcessor() {
    private lateinit var messager: Messager

    private lateinit var elementTool: Elements

    private lateinit var filer: Filer

    override fun init(processingEnv: ProcessingEnvironment) {
        messager = processingEnv.messager
        elementTool = processingEnv.elementUtils
        filer = processingEnv.filer
        super.init(processingEnv)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val elements = roundEnv.getElementsAnnotatedWith(MyARouter::class.java)
        for (ele in elements) {
            val packageName = elementTool.getPackageOf(ele).qualifiedName.toString()
            val className = ele.simpleName.toString()
            val finalClassName = "$className$$$$\$MyARouter"
            messager.printMessage(Diagnostic.Kind.NOTE, className + "被MyARouter注册")

            val myARouter = ele.getAnnotation(MyARouter::class.java)

            val findTargetClass = MethodSpec.methodBuilder("findTargetClass")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Class::class.java)
                .addParameter(String::class.java, "path")
                .addStatement("return path.equals(\$S) ? \$T.class : null",
                    myARouter.path,
                    ClassName.get(ele as TypeElement))
                .build()

            val myClass = TypeSpec.classBuilder(finalClassName)
                .addMethod(findTargetClass)
                .addModifiers(Modifier.PUBLIC)
                .build()

            val packagef = JavaFile.builder(packageName, myClass).build()

            try {
                packagef.writeTo(filer)
            } catch (e: IOException) {
                e.printStackTrace()
                messager.printMessage(Diagnostic.Kind.NOTE, "generate file fail" + e.message)
            }
        }
        return true
    }
}