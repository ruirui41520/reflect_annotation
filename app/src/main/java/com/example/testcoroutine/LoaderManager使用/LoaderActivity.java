package com.example.testcoroutine.LoaderManager使用;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        getSupportFragmentManager().beginTransaction().add(R.id.container_id,new CursorLoaderListFragment()).commit();
    }
}
