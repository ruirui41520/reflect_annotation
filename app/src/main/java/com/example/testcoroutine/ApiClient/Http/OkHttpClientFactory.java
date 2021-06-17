package com.example.testcoroutine.ApiClient.Http;

import okhttp3.OkHttpClient;

public interface OkHttpClientFactory {
    OkHttpClient create();
}
