package com.example.testcoroutine.Recyclerview使用;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;

public class CoinsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        getSupportFragmentManager().beginTransaction().add(R.id.container_id,new CoinFragment()).commit();
    }
}
