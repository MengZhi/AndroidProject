package com.example.common.base;

import android.os.Bundle;
import android.util.Log;

import com.example.common.utils.Cons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 项目父Activity
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(Cons.TAG, "common/BaseActivity");
    }
}
