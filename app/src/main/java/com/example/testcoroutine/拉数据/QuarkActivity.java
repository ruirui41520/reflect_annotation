package com.example.testcoroutine.拉数据;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.ApiClient.ApiServer.QuarkApiService;
import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.ApiClient.Subscriber.LifecycleObservedApiSubscriber;
import com.example.testcoroutine.R;

public class QuarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quark);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfoList();
    }

    private void getInfoList(){
        QuarkApiService.requestGetInfoData(new LifecycleObservedApiSubscriber<ResultContainer<SuggestionView>>(this) {
            @Override
            public void onSuccessWithValidLifecycle(ResultContainer<SuggestionView> suggestionViewResultContainer) {
                if (suggestionViewResultContainer != null){
                    if (suggestionViewResultContainer.getData() != null && suggestionViewResultContainer.getData().getStatInfo() != null){
                        ((Button)findViewById(R.id.btn_info)).setText(suggestionViewResultContainer.getData().getStatInfo().getHid());
                    }
                }
            }
        });
    }
}
