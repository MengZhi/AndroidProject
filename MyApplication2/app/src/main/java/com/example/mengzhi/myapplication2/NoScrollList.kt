package com.example.mengzhi.myapplication2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ListView

class NoScrollList: ListView {

    constructor (context: Context):super(context)

    constructor (context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var intMode = Integer.MAX_VALUE
        intMode = intMode.shr(2)

        var expandSpect = MeasureSpec.makeMeasureSpec(intMode, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpect)
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

}