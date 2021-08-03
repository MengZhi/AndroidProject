package com.example.mengzhi.myapplication2;

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.ListView


public class MyListView : ListView {
    var index = -1;

    constructor (context: Context):super(context) {
        //this.adapter = MyAdapter() as BaseExpandableListAdapter
    }

    constructor (context: Context, attrs: AttributeSet):super(context, attrs){
        //this.adapter = MyAdapter() as BaseExpandableListAdapter

    }

    override fun onFinishInflate() {
        Log.d("zhimeng", "onFinishInflate")
        super.onFinishInflate()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {


//        Log.d("zhimeng", "measuredHeight: " + measuredHeight)
//
//        val display = (context as Activity).windowManager.defaultDisplay
//        val width = display.width
//        val height = display.height
//        Log.d("zhimeng", "width = $width,height = $height")
//        Log.d("zhimeng", "Count: " + count)
//
//        index++;
//        if (index == 2) {
//            val layoutParam = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
//
//            var padding= height - measuredHeight
//            Log.d("zhimeng", "padding: " + padding)
//            var childView = getChildAt(index)
//            childView.setPadding(childView.paddingLeft, 100, childView.paddingRight, childView.paddingBottom)
//
//            var listParams = CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT)
//            listParams.height  = 1000
//            this.layoutParams = listParams
//        }
        var intMode = Integer.MAX_VALUE
        intMode = intMode.shr(2)

        var expandSpect = MeasureSpec.makeMeasureSpec(intMode, MeasureSpec.AT_MOST)



        val display = (context as Activity).windowManager.defaultDisplay
        val height = display.height
        var padding= height - measuredHeight
        Log.d("zhimeng", "ListHeight: " + measuredHeight + "Display Height: " + height)

        if (padding > 0) {
            super.onMeasure(widthMeasureSpec, expandSpect)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }


        //super.onMeasure(widthMeasureSpec, expandSpect)



    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        var lastView = getChildAt(count)
//        lastView.setBackgroundColor(Color.BLUE)
        super.onLayout(changed, l, t, r, b)
    }

    override fun onDraw(canvas: Canvas?) {
//        var lastView = getChildAt(count)
//        lastView.setBackgroundColor(Color.BLUE)
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {

        super.dispatchDraw(canvas)
    }


//    class MyAdapter: ListAdapter {
//        override fun isEmpty(): Boolean {
//            return false
//        }
//
//        override fun hasStableIds(): Boolean {
//            return false
//        }
//
//        fun getChildView(groupPosition: Int, childPosition: Int,
//                         isLastChild: Boolean, convertView: View, parent: ViewGroup): View {
//            if (childPosition == 2) {
//                convertView.setPadding(0, 0, 0, 20)
//            } else
//                convertView.setPadding(0, 0, 0, 0)
//            return convertView
//        }
//
//        fun getGroupView(groupPosition: Int, isExpanded: Boolean,
//                         convertView: View, parent: ViewGroup): View {
//            if (isExpanded)
//                convertView.setPadding(0, 0, 0, 0)
//            else
//                convertView.setPadding(0, 0, 0, 20)
//            return convertView
//        }
//
//        fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
//            return false
//        }
//    }
}
