package com.example.compiler

import com.example.compiler.utils.ProcessorConfig
import com.example.compiler.utils.ProcessorUtils
import com.example.myarouter_annotations.MyARouter
import com.example.myarouter_annotations.RouterBean
import javax.annotation.processing.*
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.example.myarouter_annotations.MyARouter")
class ARouterProcessor : AbstractProcessor() {
    private lateinit var messager: Messager

    private lateinit var elementTool: Elements

    private lateinit var filer: Filer

    private var options : String? = null // 各个模块传递过来的模块名 例如：app order personal

    private var aptPackage : String? = null // 各个模块传递过来的目录 用于统一存放 apt生成的文件

    private val allPathMap: Map<String, List<RouterBean>> = mutableMapOf()

    private val allGroupMap: Map<String, String> = mutableMapOf()

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        messager = processingEnv.messager
        elementTool = processingEnv.elementUtils
        filer = processingEnv.filer

        // 只有接受到 App壳 传递过来的书籍，才能证明我们的 APT环境搭建完成
        options = processingEnv.options[ProcessorConfig.OPTIONS]
        aptPackage = processingEnv.options[ProcessorConfig.APT_PACKAGE]
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> options:$options")
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> aptPackage:$aptPackage")

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

//            val findTargetClass = MethodSpec.methodBuilder("findTargetClass")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(Class::class.java)
//                .addParameter(String::class.java, "path")
//                .addStatement("return path.equals(\$S) ? \$T.class : null",
//                    myARouter.path,
//                    ClassName.get(ele as TypeElement))
//                .build()
//
//            val myClass = TypeSpec.classBuilder(finalClassName)
//                .addMethod(findTargetClass)
//                .addModifiers(Modifier.PUBLIC)
//                .build()
//
//            val packagef = JavaFile.builder(packageName, myClass).build()
//
//            try {
//                packagef.writeTo(filer)
//            } catch (e: IOException) {
//                e.printStackTrace()
//                messager.printMessage(Diagnostic.Kind.NOTE, "generate file fail" + e.message)
//            }
            // 在循环里面，对 “路由对象” 进行封装

            // 下面是练习 JavaPoet
            /**
             * package com.example.helloworld;
             *
             * public final class HelloWorld {
             * public static void main(String[] args) {
             * System.out.println("Hello, JavaPoet!");
             * }
             * }
             */
            // 1.方法
            /*MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();
            // 2.类
            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(mainMethod)
                    .build();
            // 3.包
            JavaFile packagef = JavaFile.builder("com.derry.study", helloWorld).build();
            // 去生成
            try {
                packagef.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "生成失败，请检查代码...");
            }*/

            // 先JavaPoet 写一个简单示例，方法--->类--> 包，是倒序写的思路哦
            /*
             package com.example.helloworld;

             public final class HelloWorld {

                 public static void main(String[] args) {
                    System.out.println("Hello, JavaPoet!");
                 }

             }
             */
            // 方法
            /*MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(void.class)
                    .addParameter(System[].class, "args")
                    // 增加main方法里面的内容
                    .addStatement("$T.out.println($S)", System.class, "AAAAAAAAAAA!")
                    .build();
            // 类  Testapp   Testorder
            TypeSpec testClass = TypeSpec.classBuilder("Test" + options)
                    .addMethod(mainMethod)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .build();
            // 包
            JavaFile packagef = JavaFile.builder("com.xiangxue.test22", testClass).build();
            try {
                packagef.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "生成Test文件时失败，异常:" + e.getMessage());
            }*/

            // TODO  一系列的检查工作
            // 在循环里面，对 “路由对象” 进行封装
            val routerBean: RouterBean = RouterBean.Builder()
                .addGroup(myARouter.group)
                .addPath(myARouter.path)
                .addElement(ele)
                .build()
        }
        return true
    }

    /**
     * 校验@ARouter注解的值，如果group未填写就从必填项path中截取数据
     * @param bean 路由详细信息，最终实体封装类
     */
    private fun checkRouterPath(bean: RouterBean): Boolean {
        val group: String? = bean.getGroup() //  同学们，一定要记住： "app"   "order"   "personal"
        val path: String? =
            bean.getPath() //  同学们，一定要记住： "/app/MainActivity"   "/order/Order_MainActivity"   "/personal/Personal_MainActivity"

        // 校验
        // @ARouter注解中的path值，必须要以 / 开头（模仿阿里Arouter规范）
        if (ProcessorUtils.isEmpty(path) || !path?.startsWith("/")!!) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的path值，必须要以 / 开头")
            return false
        }

        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity")
            return false
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app,order,personal 作为group
        val finalGroup = path.substring(1, path.indexOf("/", 1))

        // app,order,personal == options

        // @ARouter注解中的group有赋值情况
        if (!ProcessorUtils.isEmpty(group) && group != options) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！")
            return false
        } else {
            bean.setGroup(finalGroup)
        }

        // 如果真的返回ture   RouterBean.group  xxxxx 赋值成功 没有问题
        return true
    }
}