package com.example.testcoroutine.Activity栈;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;
import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;
/*
  这种模式的Activity只能单独位于一个任务栈内，由于栈内的复用特性，后续请求均不会创建新的Activity，除非这个独特的任务栈被系统销毁了,且不会受到affinity的影响
 */
public class SingleInstanceActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ViewFinder.inject(this);
        name.setText("SingleInstanceActivity");
        Log.e("****SingleInstanceActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("****SingleInstanceActivity","onStart");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("****SingleInstanceActivity","onNewIntent");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("****SingleInstanceActivity","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("****SingleInstanceActivity","onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("****SingleInstanceActivity","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("****SingleInstanceActivity","onDestroy");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("****SingleInstanceActivity","onActivityResult");

    }
}
