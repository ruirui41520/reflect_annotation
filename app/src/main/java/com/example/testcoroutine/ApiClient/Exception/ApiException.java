package com.example.testcoroutine.ApiClient.Exception;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class ApiException extends Exception{
    public ApiException() {
        super();
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        if (getCause() != null) {
            return getClass().getSimpleName() + ": " + getCause().getMessage();
        } else {
            return getClass().getSimpleName();
        }
    }

    @Override
    public String getLocalizedMessage() {
        // TODO pass resource string via case
        return super.getLocalizedMessage();
    }

    public String toResponseJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 999);
        jsonObject.addProperty("message", getMessage());
        jsonObject.add("result", JsonNull.INSTANCE);

        return jsonObject.toString();
    }
}
