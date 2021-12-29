package com.example.compiler

import com.example.compiler.utils.ProcessorConfig
import com.example.compiler.utils.ProcessorUtils
import com.example.myarouter_annotations.MyARouter
import com.example.myarouter_annotations.RouterBean
import com.google.auto.service.AutoService
import com.squareup.javapoet.*
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes(ProcessorConfig.AROUTER_PACKAGE) // 指定JDK编译版本

// 注解处理器接收的参数
@SupportedOptions(ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE)
class ARouterProcessor : AbstractProcessor() {
    private lateinit var messager: Messager
    private lateinit var elementTool: Elements
    private lateinit var typeTool: Types
    private lateinit var filer: Filer

    private var options: String? = null // 各个模块传递过来的模块名 例如：app order personal
    private var aptPackage: String? = null // 各个模块传递过来的目录 用于统一存放 apt生成的文件
    private val allPathMap = mutableMapOf<String, MutableList<RouterBean>>()
    private val allGroupMap = mutableMapOf<String, String>()

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        messager = processingEnv.messager
        elementTool = processingEnv.elementUtils
        typeTool = processingEnv.typeUtils
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
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName: " + packageName)

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

            val eleType = ele.asType()
            val activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE)
            val activityMirror = activityType.asType()
            if (typeTool.isSubtype(eleType, activityMirror)) {
                routerBean.setTypeEnum(RouterBean.TypeEnum.ACTIVITY)
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, ">>>>>>>>>>>>>" + eleType)
                // 不匹配抛出异常，这里谨慎使用！考虑维护问题
                throw RuntimeException("@MyARouter注解目前仅限用于Activity类之上")
            }

            if (checkRouterPath(routerBean)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean Check Success:$routerBean")
                // 赋值 mAllPathMap 集合里面去

                // 赋值 mAllPathMap 集合里面去
                var routerBeans: MutableList<RouterBean>? = allPathMap[routerBean.getGroup()]

                // 如果从Map中找不到key为：bean.getGroup()的数据，就新建List集合再添加进Map
                if (ProcessorUtils.isEmpty(routerBeans)) { // 仓库一 没有东西
                    routerBeans = ArrayList()
                    routerBeans.add(routerBean)
                    allPathMap[routerBean.getGroup()] = routerBeans // 加入仓库一
                } else {
                    routerBeans?.add(routerBean)
                }
            } else { // ERROR 编译期发生异常
                messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity")
            }

            val pathType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PATH)
            val groupType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_GROUP)

            // TODO 第一大步：系列PATH
            try {
                messager.printMessage(
                    Diagnostic.Kind.NOTE,
                    "pathType: ${ProcessorConfig.AROUTER_API_PATH}"
                )
                messager.printMessage(Diagnostic.Kind.NOTE, "pathType: ${pathType}")
                messager.printMessage(Diagnostic.Kind.NOTE, "groupType: ${groupType}")
                createPathFile(pathType) // 生成 Path类
            } catch (e: IOException) {
                e.printStackTrace()
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成PATH模板时，异常了 e:" + e.message)
            }

            // TODO 第二大步：组头（带头大哥）

            // TODO 第二大步：组头（带头大哥）
            try {
                createGroupFile(groupType, pathType)
            } catch (e: IOException) {
                e.printStackTrace()
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成GROUP模板时，异常了 e:" + e.message)
            }

        }
        return true
    }

    //生成如下代码
//    public class ARouter$$Path$$personal implements ARouterPath {
//        @Override
//        public Map<String, RouterBean> getPathMap() {
//            Map<String, RouterBean> pathMap = new HashMap<>();
//            pathMap.put("/personal/Personal_Main2Activity", RouterBean.create());
//            pathMap.put("/personal/Personal_MainActivity", RouterBean.create());
//            return pathMap;
//        }
//    }
    @Throws(IOException::class)
    private fun createPathFile(pathType: TypeElement) {
        // 判断 map仓库中，是否有需要生成的文件

        // 判断 map仓库中，是否有需要生成的文件
        if (ProcessorUtils.isEmpty(allPathMap)) {
            return  // 连缓存一 仓库一 里面 值都没有 不用干活了
        }

        // 生成 MutableMap<String, RouterBean> 类型的返回值
        val methodReturn = ParameterizedTypeName.get(
            ClassName.get(MutableMap::class.java),
            ClassName.get(String::class.java),
            ClassName.get(RouterBean::class.java)
        )

        allPathMap.forEach { pathMapEntry ->
            val methodBuilder = MethodSpec.methodBuilder(ProcessorConfig.PATH_METHOD_NAME)
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .returns(methodReturn)

            // Map<String, RouterBean> pathMap = new HashMap<>(); // $N == 变量 为什么是这个，因为变量有引用 所以是$N
            methodBuilder.addStatement(
                "\$T<\$T, \$T> \$N = new \$T<>()",
                ClassName.get(MutableMap::class.java),  // Map
                ClassName.get(String::class.java),  // Map<String,
                ClassName.get(RouterBean::class.java),  // Map<String, RouterBean>
                ProcessorConfig.PATH_VAR1,  // Map<String, RouterBean> pathMap
                ClassName.get(HashMap::class.java) // Map<String, RouterBean> pathMap = new HashMap<>();
            )

            // 因为有多个,所以要循环
            // pathMap.put("/personal/Personal_Main2Activity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
            // Personal_Main2Activity.class);
            // pathMap.put("/personal/Personal_MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY));
            // $N == 变量 变量有引用 所以 N
            // $L == TypeEnum.ACTIVITY
            val pathList: List<RouterBean> = pathMapEntry.value
            pathList.forEach {
                methodBuilder.addStatement(
                    "\$N.put(\$S, \$T.create(\$T.\$L, \$T.class, \$S, \$S))",
                    ProcessorConfig.PATH_VAR1,  // pathMap.put
                    it.getPath(),  // "/personal/Personal_Main2Activity"
                    ClassName.get(RouterBean::class.java),  // RouterBean
                    ClassName.get(RouterBean.TypeEnum::class.java),  // RouterBean.Type
                    it.getTypeEnum(),  // 枚举类型：ACTIVITY
                    ClassName.get(it.getElement() as? TypeElement),  // MainActivity.class Main2Activity.class
                    it.getPath(),  // 路径名
                    it.getGroup() // 组名
                )
            }

            // return pathMap;
            methodBuilder.addStatement("return \$N", ProcessorConfig.PATH_VAR1)

            // TODO 注意：不能像以前一样，1.方法，2.类  3.包， 因为这里面有implements ，所以 方法和类要合为一体生成才行，这是特殊情况
            // 最终生成的类文件名  ARouter$$Path$$personal
            val finalClassName: String = ProcessorConfig.PATH_FILE_NAME + pathMapEntry.key
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "APT生成路由Path类文件：$aptPackage.$finalClassName"
            )


            // 生成类文件：ARouter$$Path$$personal
            JavaFile.builder(
                aptPackage,  // 包名  APT 存放的路径
                TypeSpec.classBuilder(finalClassName) // 类名
                    .addSuperinterface(ClassName.get(pathType)) // 实现ARouterLoadPath接口  implements ARouterPath==pathType
                    .addModifiers(Modifier.PUBLIC) // public修饰符
                    .addMethod(methodBuilder.build()) // 方法的构建（方法参数 + 方法体）
                    .build()
            ).build() // JavaFile构建完成
                .writeTo(filer) // 文件生成器开始生成类文件

            // 仓库二 缓存二  非常重要一步，注意：PATH 路径文件生成出来了，才能赋值路由组mAllGroupMap
            allGroupMap[pathMapEntry.key] = finalClassName
        }
    }

    //生成如下代码
    //public class ARouter$$Group$$personal implements ARouterGroup {
    //  @Override
    //  public Map<String, Class<? extends ARouterPath>> getGroupMap() {
    //    Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
    //    groupMap.put("personal", ARouter$$Path$$personal.class);
    //    return groupMap;
    //  }
    //}
    @Throws(IOException::class)
    private fun createGroupFile(groupType: TypeElement, pathType: TypeElement) {
        // 仓库二 缓存二 判断是否有需要生成的类文件
        if (ProcessorUtils.isEmpty(allGroupMap) || ProcessorUtils.isEmpty(allPathMap)) return

        // 返回值 这一段 Map<String, Class<? extends ARouterPath>>
        val methodReturn = ParameterizedTypeName.get(
            ClassName.get(MutableMap::class.java),// Map
            ClassName.get(String::class.java),    // Map<String,
            ParameterizedTypeName.get(
                ClassName.get(Class::class.java), // ? extends ARouterPath
                WildcardTypeName.subtypeOf(ClassName.get(pathType))
            )
        )

        // 1.方法 public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        val methodBuidler = MethodSpec.methodBuilder(ProcessorConfig.GROUP_METHOD_NAME) // 方法名
            .addAnnotation(Override::class.java) // 重写注解 @Override
            .addModifiers(Modifier.PUBLIC) // public修饰符
            .returns(methodReturn) // 方法返回值

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement(
            "\$T<\$T, \$T> \$N = new \$T<>()",
            ClassName.get(MutableMap::class.java),
            ClassName.get(String::class.java),  // Class<? extends ARouterPath> 难度
            ParameterizedTypeName.get(
                ClassName.get(Class::class.java),
                WildcardTypeName.subtypeOf(ClassName.get(pathType))
            ),  // ? extends ARouterPath
            ProcessorConfig.GROUP_VAR1,
            ClassName.get(HashMap::class.java)
        )

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);
        for ((key, value) in allGroupMap.entries) {
            methodBuidler.addStatement(
                "\$N.put(\$S, \$T.class)",
                ProcessorConfig.GROUP_VAR1,  // groupMap.put
                key,  // order, personal ,app
                ClassName.get(aptPackage, value)
            )
        }

        // return groupMap;

        // return groupMap;
        methodBuidler.addStatement("return \$N", ProcessorConfig.GROUP_VAR1)

        // 最终生成的类文件名 ARouter$$Group$$ + personal

        // 最终生成的类文件名 ARouter$$Group$$ + personal
        val finalClassName: String = ProcessorConfig.GROUP_FILE_NAME.toString() + options

        messager.printMessage(
            Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                    aptPackage + "." + finalClassName
        )

        // 生成类文件：ARouter$$Group$$app

        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(
            aptPackage,  // 包名
            TypeSpec.classBuilder(finalClassName) // 类名
                .addSuperinterface(ClassName.get(groupType)) // 实现ARouterLoadGroup接口 implements ARouterGroup
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                .build()
        ).build().writeTo(filer) // 文件生成器开始生成类文件
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