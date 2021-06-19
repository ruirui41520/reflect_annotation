package com.example.testcoroutine.ApiClient.Api;

import com.example.testcoroutine.ApiClient.Model.AuthTokenInfo;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.ApiClient.Request.RefreshTokenRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Single;

public interface UserApi {

    @POST("/user/refreshAccessToken")
    Single<ResultContainer<AuthTokenInfo>> refreshAccessToken(@Body RefreshTokenRequest refreshToken);
}
