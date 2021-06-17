package com.example.testcoroutine;

import android.app.Application;
import android.content.Context;

import com.example.testcoroutine.ApiClient.Client;
import com.example.testcoroutine.Utils.PreferenceUtils;
import com.example.testcoroutine.插件.ClassLoaderUtil;

public class CustomApplication extends Application {
    private static CustomApplication application;
    private PreferenceUtils preferenceUtils;
    private Client apiClient;

    public static CustomApplication getInstance() {
        if (application != null) {
            return application;
        } else {
            throw new RuntimeException("Application is not attached.");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initPreferenceUtils(this);
        ClassLoaderUtil.loadClass(this);
    }

    private void initPreferenceUtils(Context context) {
        preferenceUtils = new PreferenceUtils(context);
    }

    public static <T> T getApi(Class<T> service){
        return getInstance().apiClient.getApi(service);
    }

    public static PreferenceUtils getPreferenceUtils() {
        return getInstance().preferenceUtils;
    }
}
