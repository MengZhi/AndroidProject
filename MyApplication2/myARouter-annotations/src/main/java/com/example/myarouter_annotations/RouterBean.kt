package com.example.myarouter_annotations

import javax.lang.model.element.Element

class RouterBean {
    // 为了以后的发展
    enum class TypeEnum {
        ACTIVITY
    }

    private var typeEnum // 枚举类型：Activity
            : TypeEnum? = null
    private var element // 类节点 JavaPoet学习的时候，可以拿到很多的信息
            : Element? = null
    private var myClass // 被注解的 Class对象 例如： MainActivity.class  Main2Activity.class
            : Class<*>? = null
    private var path // 路由地址  例如：/app/MainActivity
            : String? = null
    private var group = ""// 路由组  例如：app  order  personal

    // TODO 以下是一组 Get 方法
    fun getTypeEnum(): TypeEnum? {
        return typeEnum
    }

    fun getElement(): Element? {
        return element
    }

    fun getMyClass(): Class<*>? {
        return myClass
    }

    fun getPath(): String? {
        return path
    }

    fun getGroup(): String {
        return group
    }

    fun setTypeEnum(typeEnum: TypeEnum?) {
        this.typeEnum = typeEnum
    }

    fun setElement(element: Element?) {
        this.element = element
    }

    fun setMyClass(myClass: Class<*>?) {
        this.myClass = myClass
    }

    fun setPath(path: String?) {
        this.path = path
    }

    fun setGroup(group: String) {
        this.group = group
    }

    // 构建者模式相关
    private constructor(builder: Builder) {
        typeEnum = builder.type
        element = builder.element
        myClass = builder.clazz
        path = builder.path
        group = builder.group
    }

    private constructor(
        typeEnum: TypeEnum,  /*Element element,*/
        myClass: Class<*>, path: String, group: String
    ) {
        this.typeEnum = typeEnum
        // this.element = element;
        this.myClass = myClass
        this.path = path
        this.group = group
    }

    companion object {
        // 对外暴露
        // 对外提供简易版构造方法，主要是为了方便APT生成代码
        fun create(
            type: TypeEnum,
            clazz: Class<*>,
            path: String,
            group: String
        ): RouterBean {
            return RouterBean(type, clazz, path, group)
        }
    }

    /**
     * 构建者模式
     */
    class Builder {
        // 枚举类型：Activity
        var type: RouterBean.TypeEnum? = null

        // 类节点
        var element: Element? = null

        // 注解使用的类对象
        var clazz: Class<*>? = null

        // 路由地址
        var path: String? = null

        // 路由组
        var group: String = ""
        fun addType(type: TypeEnum?): Builder {
            this.type = type
            return this
        }

        fun addElement(element: Element?): Builder {
            this.element = element
            return this
        }

        fun addClazz(clazz: Class<*>?): Builder {
            this.clazz = clazz
            return this
        }

        fun addPath(path: String?): Builder {
            this.path = path
            return this
        }

        fun addGroup(group: String): Builder {
            this.group = group
            return this
        }

        // 最后的build或者create，往往是做参数的校验或者初始化赋值工作
        fun build(): RouterBean {
            require(!(path == null || path!!.isEmpty())) { "path必填项为空，如：/app/MainActivity" }
            return RouterBean(this)
        }
    }

    override fun toString(): String {
        return "RouterBean{" +
                "path='" + path + '\'' +
                ", group='" + group + '\'' +
                '}'
    }
}