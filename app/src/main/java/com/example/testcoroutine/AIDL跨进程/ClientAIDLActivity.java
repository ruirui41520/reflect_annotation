package com.example.testcoroutine.AIDL跨进程;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testcoroutine.R;
import com.example.zdd_aidlserver.IMyAidlInterface;

public class ClientAIDLActivity extends AppCompatActivity {
    IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Button loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iMyAidlInterface != null) {
                    try {
                        iMyAidlInterface.login("colin", "123456");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Intent intent = new Intent();
        intent.setAction("com.example.zdd_aidlserver");
        intent.setPackage("com.example.zdd_aidlserver");
        bindService(intent,new ConnectCallBack(), Context.BIND_AUTO_CREATE);
    }

    class ConnectCallBack implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    }
}
