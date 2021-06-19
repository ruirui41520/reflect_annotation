package com.example.testcoroutine.ApiClient.ApiServer;

import com.example.testcoroutine.ApiClient.Api.GankApi;
import com.example.testcoroutine.ApiClient.Model.GankModel.NewsEntity;
import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.ApiClient.Request.NewsRequest;
import com.example.testcoroutine.CustomApplication;
import com.example.testcoroutine.拉数据.GankRecyclerItem;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GankApiService {
    public static void requestGetInfoData(NewsRequest request, SingleSubscriber<NewsEntity> responseObserver){
        CustomApplication.getApi(GankApi.class).getNews(request.getCategory(),request.getCount(),request.getPage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseObserver);
    }
}
