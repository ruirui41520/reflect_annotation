package com.example.testcoroutine.ApiClient.Exception;

import retrofit2.adapter.rxjava.HttpException;

public class UnauthorizedException extends XApiException{

    public UnauthorizedException(int errorCode, String errorMessage, String rawContent) {
        super(errorCode, errorMessage, rawContent);
    }

    public UnauthorizedException(HttpException error) {
        super(error);
    }
}
