package com.example.mengzhi.kotlinapplication

import android.content.Context
import android.preference.Preference
import android.util.AttributeSet

class ViewPagerPreference:Preference{

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int):super(context, attrs, defStyleAttr, defStyleRes) {

    }

    constructor(context: Context, attrs: AttributeSet):super(context, attrs) {

    }

    constructor(context: Context): super(context) {

    }

}