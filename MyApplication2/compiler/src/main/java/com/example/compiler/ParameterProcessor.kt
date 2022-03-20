package com.example.compiler

import com.example.compiler.utils.ProcessorConfig
import com.example.compiler.utils.ProcessorUtils
import com.example.myarouter_annotations.MyParam
import com.squareup.javapoet.*
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

// 开启
@SupportedAnnotationTypes(ProcessorConfig.PARAMETER_PACKAGE) // 我们服务的注解
// 服务的注解
//@SupportedSourceVersion(SourceVersion.RELEASE_7) 这个是必填的
class ParameterProcessor : AbstractProcessor() {
    private lateinit var messager: Messager
    private lateinit var elementUtils: Elements
    private lateinit var typeUtils: Types
    private lateinit var filer: Filer

    // 临时map存储，用来存放被@Parameter注解的属性集合，生成类文件时遍历
    // key:类节点, value:被@Parameter注解的属性集合
    private val tempParameterMap: MutableMap<TypeElement, MutableList<Element>> = HashMap()

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        messager = processingEnv.messager
        elementUtils = processingEnv.elementUtils
        typeUtils = processingEnv.typeUtils
        filer = processingEnv.filer

        messager.printMessage(Diagnostic.Kind.NOTE, "***********************************")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        if (ProcessorUtils.isEmpty(annotations)) {
            return true
        }

        val elements = roundEnv.getElementsAnnotatedWith(MyParam::class.java)

        if (elements.isEmpty()) {
            return true
        }

        elements.forEach{
            val enclosingElement = it.enclosingElement
            if (tempParameterMap.containsKey(enclosingElement)) {
                tempParameterMap[enclosingElement]?.add(it)
            } else {
                // 没有key Personal_MainActivity
                val fields: MutableList<Element> = ArrayList()
                fields.add(it)
                tempParameterMap[enclosingElement as TypeElement] = fields // 加入缓存
            }
        }

        // 判断是否有需要生成的类文件
        if (ProcessorUtils.isEmpty(tempParameterMap)) return true

        val activityType: TypeElement =
            elementUtils.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE)
        val parameterType: TypeElement =
            elementUtils.getTypeElement(ProcessorConfig.AROUTER_AIP_PARAMETER_GET)

//最终生成如下代码
//        public class Personal_MainActivity$$Parameter implements ParameterGet {
//            @Override
//            public void getParameter(Object targetParameter) {
//                Personal_MainActivity t =(Personal_MainActivity) targetParameter;
//                t.name = t.getIntent().getStringExtra("name");
//                t.sex = t.getIntent().getStringExtra("sex");
//            }
//        }

        // 生成方法
        // Object targetParameter
        val parameterSpec =
            ParameterSpec.builder(TypeName.OBJECT, ProcessorConfig.PARAMETER_NAME).build()
        tempParameterMap.forEach {
            // key：   Personal_MainActivity
            // value： [name,sex,age]
            val typeElement: TypeElement = it.key

            // 非Activity直接报错
            // 如果类名的类型和Activity类型不匹配
            if (!typeUtils.isSubtype(typeElement.asType(), activityType.asType())) {
                throw RuntimeException("@Parameter注解目前仅限用于Activity类之上")
            }

            // 是Activity
            // 获取类名 == Personal_MainActivity
            val className = ClassName.get(typeElement)

            // 方法生成成功
            val factory: ParameterFactory = ParameterFactory.Builder(parameterSpec)
                .setMessager(messager)
                .setClassName(className)
                .build()

            // Personal_MainActivity t = (Personal_MainActivity) targetParameter;
            factory.addFirstStatement()

            // 难点 多行
            for (element in it.value) {
                factory.buildStatement(element)
            }

            // 最终生成的类文件名（类名$$Parameter） 例如：Personal_MainActivity$$Parameter
            val finalClassName: String = typeElement.simpleName.toString() + ProcessorConfig.PARAMETER_FILE_NAME

            messager.printMessage(
                Diagnostic.Kind.NOTE, "APT生成获取参数类文件：" +
                        className.packageName() + "." + finalClassName
            )

            // 开始生成文件，例如：PersonalMainActivity$$Parameter
            try {
                JavaFile.builder(
                    className.packageName(),  // 包名
                    TypeSpec.classBuilder(finalClassName) // 类名
                        .addSuperinterface(ClassName.get(parameterType)) //  implements ParameterGet 实现ParameterLoad接口
                        .addModifiers(Modifier.PUBLIC) // public修饰符
                        .addMethod(factory.build()) // 方法的构建（方法参数 + 方法体）
                        .build()
                ) // 类构建完成
                    .build() // JavaFile构建完成
                    .writeTo(filer) // 文件生成器开始生成类文件
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 1 2 3节课 true  执行两次 为了防止第二有问题 加了if (set.isEmpty()) {  内部机制回来检测一遍 所以有了第二次
        //     4节课 false 执行一次
        return false // 前几年研究 好像记得 执行一次

    }
}