package com.example.testcoroutine.ApiClient;

import android.content.Context;
import android.util.Log;

import com.example.testcoroutine.ApiClient.Http.CustomHttpFactory;
import com.example.testcoroutine.Utils.JsonUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final long DEFAULT_TIMEOUT_MS = 30000;
    private OkHttpClient originClient;
    private String defaultApiHost = null;
    private HashMap<String, Retrofit> retrofitHolder = new HashMap<>();
    private HashMap<String, Object> retrofitClientHolder = new HashMap<>();

    public Client(Context context, CustomHttpFactory httpClientFactory) {
        originClient = httpClientFactory.create();
        defaultApiHost = "http://gank.io";
    }


    public <T> T getApi(Class<T> service){
        String host = defaultApiHost;
        String serviceName = service.getName();
        String clientCacheKey = serviceName + host;
        if (!retrofitClientHolder.containsKey(clientCacheKey)){
            if (!retrofitHolder.containsKey(host)) {
                try {
                    retrofitHolder.put(host, createRetrofit(host, originClient));
                } catch (IllegalArgumentException exception) {
                    retrofitHolder.put(defaultApiHost, createRetrofit(defaultApiHost, originClient));
                }
                T ret = retrofitHolder.get(host).create(service);
                if (ret instanceof Single){
                    ((Single)ret).timeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                }
                retrofitClientHolder.put(clientCacheKey,ret);
            }
        }
        return (T)retrofitClientHolder.get(clientCacheKey);
    }

    private Retrofit createRetrofit(String host,OkHttpClient client) throws IllegalArgumentException{
        if (host == null)return null;
        return new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
    }

    public static class Builder{
        private CustomHttpFactory factory;
        private Context applicationContext;
        public Builder(){
        }
        public Builder setHttpClientFactory(CustomHttpFactory clientFactory){
            factory = clientFactory;
            return this;
        }

        public Builder setContext(Context context){
            this.applicationContext = context;
            return this;
        }

        public Client build(){
            return new Client(applicationContext,factory);
        }

    }

}
