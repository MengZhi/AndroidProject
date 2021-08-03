package com.example.mengzhi.myapplication2;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ScreenSlidePageFragment extends Fragment {
    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.viewpager_item, container, false);

        mPager = rootView.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(pagerAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            View rootView = getView();
            int[] pics = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4};
            Bundle bundle = getArguments();
            int cuttPosition = bundle.getInt("position");
            Log.d("zhimeng", "positon: " + cuttPosition);


            TextView textView = rootView.findViewById(R.id.textView);
            ImageView imageView = rootView.findViewById(R.id.item_iv);
            imageView.setImageResource(pics[cuttPosition]);

            textView.setText("hello meng zhi");
        } else {
            Log.d("zhimeng", "getView is null");
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle b = new Bundle();

            b.putInt("position", position);
            fragment.setArguments(b);
            return fragment;
        }

        public int getCount() {
            return 4;
        }
    }
}
