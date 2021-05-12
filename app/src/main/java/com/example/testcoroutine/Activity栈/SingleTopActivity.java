package com.example.testcoroutine.Activity栈;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;
import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;

/*
FLAG_ACTIVITY_SINGLE_TOP

栈顶复用模式，如果新Activity已经位于栈顶，那么此Activity不会被重新创建，同时Activity的 onNewIntent方法会被回调，如果Activity已经存在但是不再栈顶，那么和standard模式一样。
如果Activity当前是onResume状态，那么调用后会执行onPause() -> onNewIntent() -> onResume()
 */
public class SingleTopActivity extends AppCompatActivity {
    @BindView(R.id.name)
    TextView name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ViewFinder.inject(this);
        name.setText("SingleTopActivity");
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleTopActivity.this,SingleTopActivity.class));
            }
        });
        Log.e("****SingleTopActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("****SingleTopActivity","onStart");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("****SingleTopActivity","onNewIntent");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("****SingleTopActivity","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("****SingleTopActivity","onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("****SingleTopActivity","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("****SingleTopActivity","onDestroy");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("****SingleTopActivity","onActivityResult");

    }
}
