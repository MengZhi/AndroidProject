package com.example.mengzhi.lifecycletesting.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mengzhi.lifecycletesting.beans.Goods
import com.example.mengzhi.myapplication2.R

class GoodsAdapter(context: Context, girls: List<Goods>) :
    BaseAdapter() {
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    private val goods: List<Goods> = girls

    override fun getCount(): Int {
        return goods.size
    }

    override fun getItem(position: Int): Any {
        return goods[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.i(TAG, "getView position: $position")
        val view: View = inflater.inflate(R.layout.item, null)
        val g: Goods = goods[position]
        val ivIcon = view.findViewById<View>(R.id.iv_icon) as ImageView
        ivIcon.setImageResource(g.icon)
        val tvLike = view.findViewById<View>(R.id.tv_like) as TextView
        tvLike.text = "喜欢程度:" + g.like
        val tvStyle = view.findViewById<View>(R.id.tv_style) as TextView
        tvStyle.setText(g.style)
        return view
    }

    companion object {
        private const val TAG = "GoodsAdapter"
    }
}