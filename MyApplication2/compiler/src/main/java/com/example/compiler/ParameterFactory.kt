package com.example.compiler

import com.example.compiler.utils.ProcessorConfig
import com.example.compiler.utils.ProcessorUtils
import com.example.myarouter_annotations.MyParam
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

class ParameterFactory// 生成此方法
// 通过方法参数体构建方法体：public void getParameter(Object target) {
// 不想用户使用此构造函数，必须使用Builder设计模式
private constructor(builder: Builder) {
    // 方法的构建
    private var method: MethodSpec.Builder? = null

    // 类名，如：MainActivity  /  Personal_MainActivity
    private var className: ClassName? = null

    // Messager用来报告错误，警告和其他提示信息
    private var messager: Messager? = null

    init {
        messager = builder.messager
        className = builder.className
        method = MethodSpec.methodBuilder(ProcessorConfig.PARAMETER_METHOD_NAME)
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(builder.parameterSpec)
    }

    /** 只有一行
     * Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     */
    fun addFirstStatement() {
        method?.addStatement("\$T t = (\$T) " + ProcessorConfig.PARAMETER_NAME, className, className)
    }

    fun build(): MethodSpec? {
        return method?.build()
    }

    /** 多行 循环 复杂
     * 构建方体内容，如：t.s = t.getIntent.getStringExtra("s");
     * @param element 被注解的属性元素
     */
    fun buildStatement(element: Element) {
        // 遍历注解的属性节点 生成函数体
        val typeMirror = element.asType()

        // 获取 TypeKind 枚举类型的序列号
        val type = typeMirror.kind.ordinal

        // 获取属性名  name  age  sex
        val fieldName = element.simpleName.toString()

        // 获取注解的值
        var annotationValue: String? = element.getAnnotation(MyParam::class.java).name

        // 配合： t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
        // 判断注解的值为空的情况下的处理（注解中有name值就用注解值）
        annotationValue =
            if (ProcessorUtils.isEmpty(annotationValue)) fieldName else annotationValue

        // TODO 最终拼接的前缀：
        val finalValue = "t.$fieldName"

        // t.s = t.getIntent().
        // TODO t.name = t.getIntent().getStringExtra("name");
        var methodContent = "$finalValue = t.getIntent()."

        // TypeKind 枚举类型不包含String
        if (type == TypeKind.INT.ordinal) {
            // t.s = t.getIntent().getIntExtra("age", t.age);
            methodContent += "getIntExtra(\$S, $finalValue)" // 有默认值
        } else if (type == TypeKind.BOOLEAN.ordinal) {
            // t.s = t.getIntent().getBooleanExtra("isSuccess", t.age);
            methodContent += "getBooleanExtra(\$S, $finalValue)" // 有默认值
        } else { // String 类型，没有序列号的提供 需要我们自己完成
            // t.s = t.getIntent.getStringExtra("s");
            // typeMirror.toString() java.lang.String
            if (typeMirror.toString().equals(ProcessorConfig.STRING, ignoreCase = true)) {
                // String类型
                methodContent += "getStringExtra(\$S)" // 没有默认值
            }
        }

        // 健壮代码
        if (methodContent.endsWith(")")) { // 抱歉  全部的 getBooleanExtra  getIntExtra   getStringExtra
            // 参数二 9 赋值进去了
            // t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
            method!!.addStatement(methodContent, annotationValue)
        } else {
            messager!!.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参")
        }
    }

    companion object Builder {
        // Messager用来报告错误，警告和其他提示信息
        private var messager: Messager? = null

        // 类名，如：MainActivity
        private var className: ClassName? = null

        // 方法参数体
        private var parameterSpec: ParameterSpec? = null

        fun Builder(parameterSpec: ParameterSpec?): Builder {
            this.parameterSpec = parameterSpec
            return this
        }

        fun setMessager(messager: Messager?): Builder {
            this.messager = messager
            return this
        }

        fun setClassName(className: ClassName?): Builder {
            this.className = className
            return this
        }

        fun build(): ParameterFactory {
            requireNotNull(parameterSpec) { "parameterSpec方法参数体为空" }
            requireNotNull(className) { "方法内容中的className为空" }
            requireNotNull(messager) { "messager为空，Messager用来报告错误、警告和其他提示信息" }
            return ParameterFactory(this)
        }
    }
}