package com.example.testcoroutine.拉数据;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.ContentLoadingProgressBar;

import com.example.testcoroutine.ApiClient.ApiServer.GankApiService;
import com.example.testcoroutine.ApiClient.Model.GankModel.NewsEntity;
import com.example.testcoroutine.ApiClient.Model.GankModel.NewsResultEntity;
import com.example.testcoroutine.ApiClient.Request.NewsRequest;
import com.example.testcoroutine.ApiClient.Subscriber.LifecycleObservedApiSubscriber;
import com.example.testcoroutine.R;
import com.example.testcoroutine.Recyclerview使用.BaseRecyclerFragment;
import com.example.testcoroutine.Recyclerview使用.BaseRecyclerItemView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GankDataFragment extends BaseRecyclerFragment {
    private List<BaseRecyclerItemView> itemList = new ArrayList<>();
    private ContentLoadingProgressBar progressBar;
    private NewsEntity entity;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ContentLoadingProgressBar)view.findViewById(R.id.progressBar);
        getInfoList();
    }

    private void getInfoList(){
        NewsRequest request = new NewsRequest("Android",1,1);
        GankApiService.requestGetInfoData(request,new LifecycleObservedApiSubscriber<NewsEntity>(this) {
            @Override
            protected void onUnexpectedError(Throwable e) {
                super.onUnexpectedError(e);
            }

            @Override
            public void onSuccessWithValidLifecycle(NewsEntity suggestionViewResultContainer) {
                if (suggestionViewResultContainer != null){
                    entity = suggestionViewResultContainer;
                    if (entity.getError() != null && entity.getResult() != null){
                        bindGankData(entity.getResult());
                    }
                }
            }
        });
    }

    private void bindGankData(List<NewsResultEntity> newsResultEntities){
        if (newsResultEntities.size() == 0) return;
        for (int i = 0;i < newsResultEntities.size(); i++){
            itemList.add(new GankRecyclerItem(newsResultEntities.get(i)));
        }
        getAdapter().addAll(itemList);
    }
}
