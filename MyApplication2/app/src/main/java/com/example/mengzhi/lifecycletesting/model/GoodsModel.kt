package com.example.mengzhi.lifecycletesting.model

import com.example.mengzhi.lifecycletesting.beans.Goods
import com.example.mengzhi.lifecycletesting.databus.LiveDataBus
import com.example.mengzhi.myapplication2.R
import java.util.ArrayList

class GoodsModel: IGoodsModel {
    override fun loadGoodsData(onLoadListener: IGoodsModel.OnLoadListener) {
        onLoadListener.onComplete(getData())
    }

    private fun getData(): List<Goods> {
        val data: ArrayList<Goods> = ArrayList()
        //这里的数据来源于网络或数据库或其它地方
        data.add(Goods(R.drawable.s1, "一星", "****"))
        data.add(Goods(R.drawable.s2, "一星", "****"))
        data.add(Goods(R.drawable.s3, "一星", "****"))
        data.add(Goods(R.drawable.s4, "一星", "****"))
        data.add(Goods(R.drawable.s5, "一星", "****"))
        data.add(Goods(R.drawable.s6, "一星", "****"))
        data.add(Goods(R.drawable.s7, "一星", "****"))
        data.add(Goods(R.drawable.s8, "一星", "****"))
        data.add(Goods(R.drawable.s9, "一星", "****"))

        //发送消息
        LiveDataBus.liveDataBus.getInstance()?.with("list", ArrayList::class.java)?.postValue(data)
        return data
    }
}