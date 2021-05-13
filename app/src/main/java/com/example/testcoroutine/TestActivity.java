package com.example.testcoroutine;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.testcoroutine.Activity栈.SingleInstanceActivity;
import com.example.testcoroutine.Activity栈.SingleTaskActivity;
import com.example.testcoroutine.Activity栈.SingleTopActivity;
import com.example.testcoroutine.Activity栈.StandardActivity;
import com.example.testcoroutine.LruCache源码.ImageLoaderUtil;
import com.example.testcoroutine.反射.ReflectUtil;
import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;

import java.util.ArrayList;
//参考 https://github.com/bruce3x/ViewFinder.git

@ContentView(R.layout.activity_main)
public class TestActivity extends BaseActivity {
private final String path = "/sdcard/test.dex";
public static final int REQUEST_PERMISSION_CALL = 100;
FragmentManager manager = getSupportFragmentManager();

    @BindView(R.id.standard_btn)
    Button standard;

    @BindView(R.id.single_instance_btn)
    Button singleInstance;

    @BindView(R.id.single_top_btn)
    Button singleTop;

    @BindView(R.id.single_task_btn)
    Button singleTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ViewFinder.inject(this);
        if (checkPermission()){

        } else {
            startRequestPermission();
        }
        startActivity();
        if (savedInstanceState != null){
            Log.e("****InstanceState","onCreate savedInstanceState :" + savedInstanceState.getFloat("MainActivity"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startActivity(){
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, StandardActivity.class));
            }
        });
        singleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, SingleInstanceActivity.class));
            }
        });
        singleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, SingleTaskActivity.class));
            }
        });
        singleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, SingleTopActivity.class));
            }
        });
    }

    private void startRequestPermission(){
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CALL);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CALL){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    //如果拒绝授予权限,且勾选了再也不提醒
                    if (!shouldShowRequestPermissionRationale(permissions[0])){
                        AlertDialog.Builder build = new AlertDialog.Builder(this);
                        build.setTitle("说明")
                                .setMessage("需要读取内存权限")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        showTipGotoSetting();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .create().show();
                    } else {
                        showTipGotoSetting();
                    }

                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showTipGotoSetting(){
        new AlertDialog.Builder(this)
                .setTitle("内存权限不可用")
                .setMessage("请在-应用设置-权限-中，允许APP使用内存读取权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false).show();
    }


    /**
     * 打开Setting
     */
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("MainActivity",10.0f);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //屏幕旋转切换
        super.onRestoreInstanceState(savedInstanceState);
    }
}
