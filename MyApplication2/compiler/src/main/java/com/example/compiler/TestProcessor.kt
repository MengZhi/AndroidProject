package com.example.compiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.example.mengzhi.annotation.intdef.MyAnnotation")
class TestProcessor:AbstractProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val msger = processingEnv.messager
        msger.printMessage(Diagnostic.Kind.NOTE, "===========")

        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes()
    }
}