package com.example.mengzhi.myapplication2

import android.content.Context
import android.preference.Preference
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HiTouchVersionPreference : Preference {
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        widgetLayoutResource = R.layout.hitouch_version
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        HiTouchVersionPreference(context, attrs, 0)

    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        var versionNum = view?.findViewById<TextView>(R.id.hitouch_version)

        versionNum?.text = "HiTouch 11.0.3.202"


    }
}