package com.example.testcoroutine.ApiClient.Exception;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class NetworkException extends ApiException {

    public NetworkException() {
    }

    public NetworkException(Throwable throwable) {
        super(throwable);
    }


    public String toResponseJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 888);
        jsonObject.addProperty("message", getMessage());
        jsonObject.add("result", JsonNull.INSTANCE);

        return jsonObject.toString();
    }
}

