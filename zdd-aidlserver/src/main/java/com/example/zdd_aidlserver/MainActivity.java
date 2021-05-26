package com.example.zdd_aidlserver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AIDLServer.OnLoginListener {
    private TextView view;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.tv);
        Intent intent = new Intent(this, AIDLServer.class);
        bindService(intent, mAIDLConnection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AIDLServer.MyBinder binder = (AIDLServer.MyBinder) service;
            binder.getService().setmOnLoginListener(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void login(final String username, final String password) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                view.setText(username + ", " + password);
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}
