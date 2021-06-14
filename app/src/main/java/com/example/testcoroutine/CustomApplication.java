package com.example.testcoroutine;

import android.app.Application;

import com.example.testcoroutine.插件.ClassLoaderUtil;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClassLoaderUtil.loadClass(this);
    }
}
