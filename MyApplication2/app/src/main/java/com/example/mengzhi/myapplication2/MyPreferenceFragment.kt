package com.example.mengzhi.myapplication2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceFragment


class MyPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //addPreferencesFromResource(R.xml.preference_screen)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        if (view != null) {
//            var list :ListView =  view.findViewById(android.R.id.list)
//            setListViewHight(list)
//        }
//    }
//
//    fun setListViewHight(listView:ListView) {
//        // 获取ListView对应的Adapter
//        var listAdapter:ListAdapter = listView.getAdapter();
//
//        var count = listAdapter.getCount()
//        Log.i("zhimeng", "count: " + count)
//        for (i in 0 until count) {
//            Log.i("zhimeng", "i: " + i)
//            // listAdapter.getCount()返回数据项的数目
//            var itemView:View = listAdapter.getView(i,null,listView);
////            itemView.measure(0,0);
////            // 统计所有子项的总高度
////            totalHeight += itemView.getMeasuredHeight();
//            itemView.setBackgroundColor(Color.CYAN)
//            var text:TextView = itemView.findViewById(android.R.id.summary)
//            text.setTextColor(Color.RED)
//            text.text = "zhimengnihao"
//
//            var title:TextView = itemView.findViewById(android.R.id.title)
//            title.setBackgroundColor(Color.CYAN)
//        }
//        listView.adapter = listAdapter
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
////        var params:ViewGroup.LayoutParams = listView.getLayoutParams();
////        params.height = totalHeight +(listView.getDividerHeight()*(listAdapter.getCount()-1));
////        listView.setLayoutParams(params);
//    }
}