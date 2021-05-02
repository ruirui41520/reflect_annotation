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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zdd_viewinjector.ViewFinder;
import com.example.zdd_viewinjector_annotation.BindView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;
//参考 https://github.com/bruce3x/ViewFinder.git

@ContentView(R.layout.activity_main)
public class TestActivity extends BaseActivity {
private final String path = "/sdcard/test.dex";
public static final int REQUEST_PERMISSION_CALL = 100;

    @BindView(R.id.show)TextView showView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.inject(this);
        showView.setText("annotation");
        if (checkPermission()){
            launchClass();
        } else {
            startRequestPermission();
        }
    }

    private void launchClass(){
        PathClassLoader classLoader = new PathClassLoader(path, getClassLoader());
        try {
            Class<?> testClass = classLoader.loadClass("com.example.zdd_plugin.TestPlugin");
            Method sayHiMethod = testClass.getMethod("print");
            sayHiMethod.invoke(null);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    private void startRequestPermission(){
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CALL);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                    launchClass();
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
}
