package com.example.testcoroutine.ApiClient.Http;

import com.example.testcoroutine.ApiClient.NetWork.ServerConfig;
import com.example.testcoroutine.Utils.PreferenceUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRequestInterceptor implements Interceptor {
    private PreferenceUtils prefUtils;
    private String quarkBaseUrl;
    private static final String LANGUAGE_HEADER = "Accept-Language";
    private static final String USER_AGENT_HEADER = "User-Agent";
    public ApiRequestInterceptor(PreferenceUtils prefUtils) {
        this.prefUtils = prefUtils;
        quarkBaseUrl = "https://quark.sm.cn";
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String requestUrl = request.url().toString();
        if (requestUrl.startsWith(quarkBaseUrl)){
            builder.url(request.url());
        }
        overrideUserAgentHeader(builder);
        overrideAcceptLanguageHeader(builder);
        applyRequestHeaders(builder);
        return chain.proceed(builder.build());
    }

    private void overrideAcceptLanguageHeader(Request.Builder builder){
        builder.addHeader(LANGUAGE_HEADER, Locale.getDefault().getLanguage());
    }

    private void overrideUserAgentHeader(Request.Builder builder) {
        builder.removeHeader(USER_AGENT_HEADER).addHeader(USER_AGENT_HEADER, "");
    }

    private void applyRequestHeaders(Request.Builder builder){
        if (false){
            applyHeader(builder, ServerConfig.LINE_LIVE_ACCESS_TOKEN, prefUtils.getApiToken());
        }
    }

    private void applyHeader(Request.Builder builder, String name, String value) {
        if (name != null && value != null) {
            builder.addHeader(name, value);
        }
    }

}
