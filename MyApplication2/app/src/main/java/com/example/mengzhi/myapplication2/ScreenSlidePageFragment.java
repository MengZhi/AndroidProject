package com.example.mengzhi.myapplication2;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.viewpager_item, container, false);

        int[] pics = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4};
        Bundle bundle = getArguments();
        int cuttPosition = bundle.getInt("position");
        Log.d("zhimeng", "positon: " + cuttPosition);


        TextView textView = rootView.findViewById(R.id.textView);
        ImageView imageView = rootView.findViewById(R.id.item_iv);
        imageView.setImageResource(pics[cuttPosition]);

        textView.setText("hello meng zhi");

        return rootView;
    }
}
