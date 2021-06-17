package com.example.testcoroutine.ApiClient.Subscriber;

import com.example.testcoroutine.ApiClient.Exception.NetworkException;
import com.example.testcoroutine.ApiClient.Exception.UnauthorizedException;

import rx.SingleSubscriber;

public abstract class ApiClientSubscriber<T> extends SingleSubscriber<T> {

    protected void onUnexpectedError(Throwable e) {
    }

    protected boolean onNetworkError(NetworkException e) {
        return false;
    }

    protected boolean onUnauthorized(UnauthorizedException e) {
        return false;
    }

    @Override
    public void onError(Throwable e) {
        boolean isHandled = false;
        if (e != null) {
            if (e instanceof NetworkException) {
                isHandled = onNetworkError((NetworkException) e);
            } else if (e instanceof UnauthorizedException) {
                isHandled = onUnauthorized((UnauthorizedException) e);
            }
        }
        if (!isHandled) {
            onUnexpectedError(e);
        }
    }
}
