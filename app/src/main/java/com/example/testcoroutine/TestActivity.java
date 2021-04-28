package com.example.testcoroutine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;
//参考 https://github.com/bruce3x/ViewFinder.git

@ContentView(R.layout.activity_main)
public class TestActivity extends BaseActivity {

    @BindView(R.id.show)TextView showView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.inject(this);
        showView.setText("annotation test");
    }

}
