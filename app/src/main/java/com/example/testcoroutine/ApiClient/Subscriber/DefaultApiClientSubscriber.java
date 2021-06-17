package com.example.testcoroutine.ApiClient.Subscriber;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.testcoroutine.ApiClient.Exception.UnauthorizedException;

import java.lang.ref.WeakReference;

public abstract class DefaultApiClientSubscriber<T> extends ApiClientSubscriber<T> {

    private WeakReference<Context> contextRef;

    public DefaultApiClientSubscriber(Context context) {
        super();
        contextRef = new WeakReference<>(context);
    }

    @Override
    public boolean onUnauthorized(UnauthorizedException e) {
        final Context context = getContext();
        if (context != null) {
//            if (LoginUtils.isLoggedIn()) {
//                LoginUtils.logout();
//            }
        }
        return true;
    }


    @Nullable
    public Context getContext() {
        return contextRef.get();
    }
}
