package com.example.testcoroutine.拉数据;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testcoroutine.R;

public class GankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quark);
        getSupportFragmentManager().beginTransaction().add(R.id.container_id,new GankDataFragment()).commit();
    }
}
