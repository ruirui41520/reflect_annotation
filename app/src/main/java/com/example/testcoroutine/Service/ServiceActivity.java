package com.example.testcoroutine.Service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;

public class ServiceActivity extends AppCompatActivity {
    private CustomService.IBindWorker worker = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            worker = (CustomService.IBindWorker)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(ServiceActivity.this,CustomService.class),mConnection, Service.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this,CustomService.class);
                intent.setAction(CustomService.ACTION_LOG);
                intent.putExtra(CustomService.ACTION_KEY_LOG_MSG,"i'm custom service");
                startService(intent);
            }
        });
    }

    private void unBindService(){
        if (worker != null){
            unbindService(mConnection);
        }
    }

    private void stopService(){
        Intent intent = new Intent(ServiceActivity.this,CustomService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
        stopService();
    }
}
