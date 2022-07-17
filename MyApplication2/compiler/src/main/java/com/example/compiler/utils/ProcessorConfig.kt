package com.example.compiler.utils

object ProcessorConfig {


    // @ARouter注解 的 包名 + 类名
    const val AROUTER_PACKAGE = "com.example.myarouter_annotations.MyARouter"

    const val PARAMETER_PACKAGE = "com.example.myarouter_annotations.MyParam"

    // ARouter api 的 ParameterGet 方法参数的名字
    var PARAMETER_NAME = "targetParameter"

    // 接收参数的TAG标记
    const val OPTIONS = "moduleName" // 同学们：目的是接收 每个module名称

    const val APT_PACKAGE = "packageNameForAPT" // 同学们：目的是接收 包名（APT 存放的包名）

    const val ACTIVITY_PACKAGE = "android.app.Activity"

    // ARouter api 包名
    var AROUTER_API_PACKAGE = "com.example.myarouterapi"

    // ARouter api 的 ARouterGroup 高层标准
    var AROUTER_API_GROUP: String = "$AROUTER_API_PACKAGE.ARouterGroup"

    // ARouter api 的 ARouterPath 高层标准
    var AROUTER_API_PATH: String = "$AROUTER_API_PACKAGE.ARouterPath"

    // ARouter api 的 ParameterGet 高层标准
    var AROUTER_AIP_PARAMETER_GET = "$AROUTER_API_PACKAGE.ParameterGet"

    // ARouter api 的 ParameterGet 高层标准
    var AROUTER_AIP_CALL = "$AROUTER_API_PACKAGE.Call"

    // 路由组，中的 Path 里面的 方法名
    var PATH_METHOD_NAME = "getPathMap"

    // 路由组，中的 Group 里面的 方法名
    var GROUP_METHOD_NAME = "getGroupMap"

    // ARouter api 的 ParmeterGet 方法的名字
    var PARAMETER_METHOD_NAME = "getParameter"

    // 路由组，中的 Path 里面 的 变量名 1
    var PATH_VAR1 = "pathMap"

    // 路由组，中的 Group 里面 的 变量名 1
    var GROUP_VAR1 = "groupMap"

    // 路由组，PATH 最终要生成的 文件名
    var PATH_FILE_NAME = "ARouter\$\$Path\$\$"

    // 路由组，GROUP 最终要生成的 文件名
    var GROUP_FILE_NAME = "ARouter$\$Group$$"

    // String全类名
    const val STRING = "java.lang.String"

    // ARouter aip 的 ParmeterGet 的 生成文件名称 $$Parameter
    var PARAMETER_FILE_NAME = "$\$Parameter"
}