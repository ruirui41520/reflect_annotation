package com.example.testcoroutine.ApiClient.ApiServer;

import com.example.testcoroutine.ApiClient.Api.QuarkApi;
import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.CustomApplication;

import rx.SingleSubscriber;

public class QuarkApiService {
    public static void requestGetInfoData(SingleSubscriber<ResultContainer<SuggestionView>> responseObserver){
        ApiRequester.instance().request(CustomApplication.getApi(QuarkApi.class).getQuarkInfoList(),responseObserver);
    }
}
