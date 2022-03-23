package com.example.mengzhi.lifecycletesting.view

import com.example.mengzhi.lifecycletesting.beans.Goods

interface IGoodsView : IBaseView{
    fun showGoodsView(goods: List<Goods>)
}