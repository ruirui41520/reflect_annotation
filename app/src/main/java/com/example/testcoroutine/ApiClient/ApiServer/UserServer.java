package com.example.testcoroutine.ApiClient.ApiServer;

import com.example.testcoroutine.ApiClient.Api.UserApi;
import com.example.testcoroutine.ApiClient.Model.AuthTokenInfo;
import com.example.testcoroutine.ApiClient.Model.IResultContainer;
import com.example.testcoroutine.ApiClient.Request.RefreshTokenRequest;
import com.example.testcoroutine.ApiClient.Subscriber.ApiClientSubscriber;
import com.example.testcoroutine.CustomApplication;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserServer {

    public static void requestRefreshAccessToken(RefreshTokenRequest refreshToken, ApiClientSubscriber<IResultContainer<AuthTokenInfo>> responseObserver) {
        CustomApplication.getApi(UserApi.class)
                .refreshAccessToken(refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseObserver);
    }
}
