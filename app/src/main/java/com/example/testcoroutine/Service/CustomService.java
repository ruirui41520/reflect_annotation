package com.example.testcoroutine.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

public class CustomService extends Service {
    private static final String TAG = CustomService.class.getSimpleName();
    public static final String ACTION_LOG = "com.android.action.log";
    public static final String ACTION_KEY_LOG_MSG = "com.android.action.log.msg";
    public interface IBindWorker{
        void add(int a,int b);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("CustomService","onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("CustomService","onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("CustomService","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("CustomService","onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("CustomService","onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        Log.e("CustomService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private static class MyBinder extends Binder implements IBindWorker{

        @Override
        public void add(int a, int b) {
            System.err.print("********value : " + (a + b));
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (!TextUtils
                .isEmpty(action)) {
            switch (action) {
                case ACTION_LOG:
                    Log.d(TAG, intent.getStringExtra(ACTION_KEY_LOG_MSG));
                    break;
                default:
                    break;
            }
        }
    }
}
