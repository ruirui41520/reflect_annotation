package com.example.testcoroutine.Activity栈;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;
import com.example.testcoroutine.TestActivity;
import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;

/*
FLAG_ACTIVITY_NEW_TASK

栈内复用模式，创建这样的Activity，系统会确认它所需任务栈是否已经创建，否则先创建任务栈，然后放入Activity，如果栈中已经有一个Activity实例，那么会做两件事
1.这个Activity会回到栈顶执行onNewIntent->onStart->onResume
2.清理在当前Activity上面的所有Activity
上面的如果栈中已经有一个Activity实例，这个判断条件的标准是由android:taskAffinity决定的
 */

public class SingleTaskActivity extends AppCompatActivity {
    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ViewFinder.inject(this);
        name.setText("SingleTaskActivity");
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleTaskActivity.this, TestActivity.class));
            }
        });
        Log.e("****SingleTaskActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("****SingleTaskActivity","onStart");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("****SingleTaskActivity","onNewIntent");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("****SingleTaskActivity","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("****SingleTaskActivity","onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("****SingleTaskActivity","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("****SingleTaskActivity","onDestroy");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("****SingleTaskActivity","onActivityResult");

    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        super.onBackPressed();
    }
}
