package com.example.mengzhi.myapplication2;

import android.content.Context;
import android.graphics.Color;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MySwitchPreference extends SwitchPreference {
    public MySwitchPreference(Context context) {
        super(context);
    }
    public MySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MySwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        view.setVerticalScrollBarEnabled(false);
        view.setScrollContainer(false);

        TextView summary = view.findViewById(android.R.id.summary);
        TextView title = view.findViewById(android.R.id.title);
        summary.setBackgroundColor(Color.CYAN);
        title.setBackgroundColor(Color.RED);
        //Log.d("zhimeng", "summary.getLineCount(): "+summary.getLineCount());
    }


}
