package com.example.testcoroutine.ApiClient.Exception;

import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.LogUtil.PL;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class XApiException extends ApiException {

    private int errorCode;

    private String errorMessage;

    private String rawContent;

    public XApiException(int errorCode, String errorMessage, String rawContent) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.rawContent = rawContent;
    }

    public XApiException(HttpException error) {
        if (null != error) {
            try {
                this.rawContent = error.response().errorBody().string();
                ResultContainer errorResponse = getErrorBodyAs(getRawContent(), ResultContainer.class);
                this.errorCode = errorResponse.getStatus();
                this.errorMessage = errorResponse.getMsg();
            } catch (IOException e) {
                PL.ex(e);
            }
        }
    }

    @Override
    public String getMessage() {
        return getErrorMessage() != null ? getErrorMessage() : super.getMessage();
    }

    protected static <T> T getErrorBodyAs(String json, Class<T> type) throws IOException {
        if (json == null) {
            return null;
        }
        return new Gson().getAdapter(type).fromJson(json);
    }

    @Override
    public String toResponseJson() {
        return getRawContent();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRawContent() {
        return rawContent;
    }
}
