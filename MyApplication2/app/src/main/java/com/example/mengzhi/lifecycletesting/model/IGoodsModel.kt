package com.example.mengzhi.lifecycletesting.model

import com.example.mengzhi.lifecycletesting.beans.Goods

interface IGoodsModel {
    fun loadGoodsData(onLoadListener: OnLoadListener)
    interface OnLoadListener {
        fun onComplete(goods: List<Goods>)
        fun onError(msg: String)
    }
}