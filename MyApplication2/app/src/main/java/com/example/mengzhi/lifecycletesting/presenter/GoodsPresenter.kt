package com.example.mengzhi.lifecycletesting.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.mengzhi.lifecycletesting.beans.Goods
import com.example.mengzhi.lifecycletesting.model.GoodsModel
import com.example.mengzhi.lifecycletesting.model.IGoodsModel
import com.example.mengzhi.lifecycletesting.view.IGoodsView

class GoodsPresenter<T : IGoodsView> : BasePresenter<T>() {
    var iGoodsModel: IGoodsModel = GoodsModel()

    /**
     * 执行业务逻辑
     */
    fun fetch() {
        if (iGoodsView != null) {
            iGoodsModel.loadGoodsData(object : IGoodsModel.OnLoadListener {
                override fun onComplete(goods: List<Goods>) {
                    (iGoodsView!!.get() as IGoodsView).showGoodsView(goods)
                }

                override fun onError(msg: String) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun onCreateX(owner: LifecycleOwner) {
        super.onCreateX(owner)
        Log.i("jett", "create")
    }

    override fun onDestory(owner: LifecycleOwner) {
        super.onDestory(owner)
        Log.i("jett", "destroy")
    }
}