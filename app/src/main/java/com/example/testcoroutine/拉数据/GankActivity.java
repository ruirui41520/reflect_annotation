package com.example.testcoroutine.拉数据;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.ApiClient.ApiServer.GankApiService;
import com.example.testcoroutine.ApiClient.Model.GankModel.NewsEntity;
import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.ApiClient.Request.NewsRequest;
import com.example.testcoroutine.ApiClient.Subscriber.LifecycleObservedApiSubscriber;
import com.example.testcoroutine.R;
import com.example.testcoroutine.Recyclerview使用.CoinFragment;

public class GankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quark);
        getSupportFragmentManager().beginTransaction().add(R.id.container_id,new GankDataFragment()).commit();
    }
}
