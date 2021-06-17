package com.example.testcoroutine.ApiClient.Http;

import com.example.testcoroutine.ApiClient.MemoryCookie;
import com.example.testcoroutine.Utils.PreferenceUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CustomHttpFactory implements OkHttpClientFactory {
    private PreferenceUtils prefUtils;
    private static final long DEFAULT_TIMEOUT_MS = 30_000;

    public void setTimeoutMs(long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public long getTimeoutMs() {
        return timeoutMs;
    }

    private long timeoutMs = DEFAULT_TIMEOUT_MS;

    public CustomHttpFactory(PreferenceUtils prefUtils) {
        this.prefUtils = prefUtils;
    }

    @Override
    public OkHttpClient create() {
        return new OkHttpClient.Builder()
                .cookieJar(new MemoryCookie())
                .addInterceptor(new ApiRequestInterceptor(prefUtils))
                .addInterceptor(new OkHttpLogIntercept())
                .connectTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .readTimeout(timeoutMs,TimeUnit.MILLISECONDS)
                .build();
    }
}
