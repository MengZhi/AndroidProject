package com.example.myarouterapi

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.util.LruCache
import com.example.myarouter_annotations.RouterBean

class RouterManager private constructor() {
    private lateinit var group: String // 路由的组名 app，order，personal ...
    private lateinit var path: String // 路由的路径  例如：/order/Order_MainActivity

    // 提供性能  LRU缓存
    private var groupLruCache: LruCache<String, ARouterGroup?> = LruCache(100)
    private var pathLruCache: LruCache<String, ARouterPath?> = LruCache(100)

    /***
     * @param path 例如：/order/Order_MainActivity
     * * @return
     */
    fun build(path: String): BundleManager {
        require(!(TextUtils.isEmpty(path) || !path.startsWith("/"))) { "不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity" }

        // 同学可以自己增加
        // ...
        require(path.lastIndexOf("/") != 0) {  // 只写了一个 /
            "不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity"
        }

        // 截取组名  /order/Order_MainActivity  finalGroup=order
        val finalGroup = path.substring(1, path.indexOf("/", 1)) // finalGroup = order
        require(!TextUtils.isEmpty(finalGroup)) { "不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity" }

        // 证明没有问题，没有抛出异常
        this.path = path // 最终的效果：如 /order/Order_MainActivity
        group = finalGroup // 例如：order，personal

        // TODO 走到这里后  grooup 和 path 没有任何问题   app，order，personal      /app/MainActivity
        return BundleManager() // Builder设计模式 之前是写里面的， 现在写外面吧
    }

    fun navigation(context: Context, bundleManager: BundleManager): Any? {
        // 例如：寻找 ARouter$$Group$$personal  寻址   ARouter$$Group$$order   ARouter$$Group$$app
        val groupClassName =
            context.packageName + "." + "new_modular_customarouter" + "." + FILE_GROUP_NAME + group
        Log.e(TAG, "navigation: groupClassName=$groupClassName")

        try {

            // TODO 第一步 读取路由组Group类文件
            var loadGroup: ARouterGroup? = groupLruCache.get(group)
            if (null == loadGroup) {
                // 加载APT路由组Group类文件 例如：ARouter$$Group$$order
                val aClass = Class.forName(groupClassName)
                // 初始化类文件
                loadGroup = aClass.newInstance() as ARouterGroup

                // 保存到缓存
                groupLruCache.put(group, loadGroup)
            }

            if (loadGroup.getGroupMap().isEmpty()) {
                throw RuntimeException("路由表Group报废了...") // Group这个类 加载失败
            }

            // TODO 第二步 读取路由Path类文件
            var loadPath = pathLruCache[path]
            if (null == loadPath) {
                // 1.invoke loadGroup
                // 2.Map<String, Class<? extends ARouterLoadPath>>
                val clazz = loadGroup.getGroupMap()[group]

                // 3.从map里面获取 ARouter$$Path$$personal.class
                loadPath = clazz?.newInstance()

                // 保存到缓存
                pathLruCache.put(path, loadPath)
            }

            // TODO 第三步 跳转
            if (loadPath != null) { // 健壮
                if (loadPath.getPathMap().isEmpty()) { // pathMap.get("key") == null
                    throw RuntimeException("路由表Path报废了...")
                }

                // 最后才执行操作
                val routerBean: RouterBean? = loadPath.getPathMap()[path]
                if (routerBean != null) {
                    when (routerBean.getTypeEnum()) {
                        RouterBean.TypeEnum.ACTIVITY -> {
                            val intent = Intent(
                                context,
                                routerBean.getMyClass()
                            ) // 例如：getClazz == Order_MainActivity.class
                            intent.putExtras(bundleManager.getBundle()) // 携带参数
                            context.startActivity(intent, bundleManager.getBundle())
                        }
                    }
                }
            }

        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
        }
        return null
    }

    companion object {
        private const val TAG = "RouterManager"
        private var instance: RouterManager? = null

        // 为了拼接，例如:ARouter$$Group$$personal
        private const val FILE_GROUP_NAME = "ARouter\$\$Group\$\$"

        fun getInstance(): RouterManager? {
            if (instance == null) {
                synchronized(RouterManager::class.java) {
                    if (instance == null) {
                        instance = RouterManager()
                    }
                }
            }
            return instance
        }
    }
}