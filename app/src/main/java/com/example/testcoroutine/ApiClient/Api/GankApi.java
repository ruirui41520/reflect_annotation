package com.example.testcoroutine.ApiClient.Api;

import com.example.testcoroutine.ApiClient.Model.GankModel.NewsEntity;
import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;

import retrofit2.http.Path;
import rx.Single;
import retrofit2.http.GET;

public interface GankApi {

    @GET("/api/data/{category}/{count}/{page}")
    Single<NewsEntity> getNews(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
