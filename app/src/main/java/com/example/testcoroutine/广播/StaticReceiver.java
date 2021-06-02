package com.example.testcoroutine.广播;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.broadcast.static")){
            String msg = intent.getStringExtra("test");
            Log.e("******test", msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}
