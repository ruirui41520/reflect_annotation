package com.example.testcoroutine;

import android.app.Application;
import android.content.Context;

import com.example.testcoroutine.ApiClient.Client;
import com.example.testcoroutine.ApiClient.Http.CustomHttpFactory;
import com.example.testcoroutine.Utils.PreferenceUtils;
import com.example.testcoroutine.插件.ClassLoaderUtil;

public class CustomApplication extends Application {
    private static CustomApplication application;
    private PreferenceUtils preferenceUtils;
    private static Client apiClient;

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
        initPreferenceUtils();
        initApiClient();
        ClassLoaderUtil.loadClass(application);
    }

    private void initPreferenceUtils() {
        preferenceUtils = new PreferenceUtils(application);
    }

    public static <T> T getApi(Class<T> service){
        return getInstance().apiClient.getApi(service);
    }

    private void initApiClient(){
        apiClient = new Client.Builder()
                .setContext(application)
                .setHttpClientFactory(new CustomHttpFactory(getPreferenceUtils()))
                .build();
    }

    public static PreferenceUtils getPreferenceUtils() {
        return getInstance().preferenceUtils;
    }

    public static Client getApiClient(){
        if (apiClient != null){
            return apiClient;
        } else {
            throw new RuntimeException("Application is not attached.");
        }
    }
}
