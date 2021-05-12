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
标准模式，每次启动Activity都会创建一个新的Activity实例，并且将其压入任务栈栈顶，而不管这个 Activity 是否已经存在，都会执行onCreate() ->onStart() -> onResume
 */


public class StandardActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ViewFinder.inject(this);
        name.setText("StandardActivity");
        Log.e("****StandardActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("****StandardActivity","onStart");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("****StandardActivity","onNewIntent");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("****StandardActivity","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("****StandardActivity","onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("****StandardActivity","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("****StandardActivity","onDestroy");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("****StandardActivity","onActivityResult");

    }
}
