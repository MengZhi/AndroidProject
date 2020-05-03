package com.example.mengzhi.myapplication2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.support.v4.app.*
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.preference.PreferenceFragmentCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ViewPagerPreference:Preference{
    private lateinit var mContext:Context


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        ViewPagerPreference(context, attrs, 0)
        widgetLayoutResource = R.layout.view_pager_layout
        mContext = context
        Log.d("zhimeng", "widgetLayoutResource setted")
    }

    override fun onBindView(view: View) {
        super.onBindView(view)

        val viewPager = view.findViewById<View>(R.id.pager) as ViewPager
        viewPager.adapter = CustomPagerAdapter(mContext)
    }


    inner class CustomPagerAdapter(private val mContext: Context) : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val rootView = inflater.inflate(R.layout.viewpager_item, collection, false) as ViewGroup
            val pics = intArrayOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4)


            val textView = rootView.findViewById<TextView>(R.id.textView)
            val imageView = rootView.findViewById<ImageView>(R.id.item_iv)
            imageView.setImageResource(pics[position])
            textView.text = "Hello zhimeng"
            collection.addView(rootView)
            return rootView
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}