package com.example.compiler

import java.sql.Types
import javax.annotation.processing.*
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.example.myarouter_annotations.MyARouter")
@SupportedOptions("student")
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
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {

        messager.printMessage(Diagnostic.Kind.NOTE, "===========")

        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes()
    }
}