package com.example.mengzhi.myapplication2;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitouch_version);
        View view = findViewById(R.id.version);

        ObjectAnimator animEnter = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.anim_enter);
        ObjectAnimator animExit = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.anim_exit);
        ObjectAnimator animShow = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.anim_show);
        ObjectAnimator animHide = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.anim_hide);

        ScaleAnimation animScaleSmall = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_scale_small);
        ScaleAnimation animScaleLarge = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_scale_large);
        view.startAnimation(animScaleSmall);
        view.startAnimation(animScaleLarge);



        view.setBackgroundResource(R.drawable.trans_color);
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animHide).after(500L).after(animShow);
        animatorSet.setTarget(view);
        animatorSet.start();

//        AnimatorSet animatorSet2 = new AnimatorSet();
//        animatorSet2.play(animExit).after(5000L).after(animEnter);
//        animatorSet2.setTarget(view);
//        animatorSet2.start();
    }
}