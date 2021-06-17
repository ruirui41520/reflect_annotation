package com.example.testcoroutine.ApiClient;

import com.example.testcoroutine.ApiClient.Exception.UnauthorizedException;
import com.example.testcoroutine.ApiClient.Exception.XApiException;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ApiErrorResolver {

    public static XApiException asXApiException(HttpException error) throws IOException {
        switch (error.code()) {
            case 401:
                return new UnauthorizedException(error);
            default:
                return new XApiException(error);
        }
    }

    public static XApiException asXApiException(int errorCode) throws IOException {
        switch (errorCode) {
            case 401:
                return new UnauthorizedException(null);
            default:
                return new XApiException(null);
        }
    }
}
