package com.example.testcoroutine.ApiClient.Http;

import com.example.testcoroutine.LogUtil.PL;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpLogIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        loggingRequest(request);
        Response response = chain.proceed(request);
        loggingResponse(response);
        return response;
    }


    private void loggingRequest(Request request){
        try {
            if (request.url().toString().startsWith("https:")){
                PL.d("XXX","TODO " + request.url());
            }
            StringBuilder builder = new StringBuilder()
                    .append("HTTP -->")
                    .append(request.url())
                    .append(" ")
                    .append(request.method());
            PL.i(builder.toString());
        }catch (Throwable ignore){

        }
    }

    private void loggingResponse(Response response){
        try {
            StringBuilder sbRes = new StringBuilder()
                    .append("[HTTP] <-- ")
                    .append(response.request().url())
                    .append(" code=")
                    .append(response.code());
            PL.i(sbRes.toString());
        }catch (Throwable throwable){
            PL.ex(throwable);
        }

    }
}
