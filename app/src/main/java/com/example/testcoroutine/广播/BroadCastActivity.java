package com.example.testcoroutine.广播;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;

public class BroadCastActivity extends AppCompatActivity {
    BroadcastReceiver mDynamicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.broadcast.dynamic")){
                String msg = intent.getStringExtra("test");
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        IntentFilter filter  = new IntentFilter();
        filter.addAction("com.broadcast.dynamic");
        registerReceiver(mDynamicReceiver,filter);

        findViewById(R.id.sendMessageStatic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.broadcast.static");
                intent.putExtra("test","colin static");
                sendBroadcast(intent);
            }
        });

        findViewById(R.id.sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.broadcast.dynamic");
                intent.putExtra("test","colin dynamic");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mDynamicReceiver);
    }
}
