package com.example.zdd_aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLServer extends Service {
    public void setmOnLoginListener(OnLoginListener mOnLoginListener) {
        this.mOnLoginListener = mOnLoginListener;
    }

    private OnLoginListener mOnLoginListener;

    public AIDLServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public interface OnLoginListener {
        void login(String username, String password);
    }


    class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void login(String userName, String password) throws RemoteException {
            if (mOnLoginListener != null){
                mOnLoginListener.login(userName,password);
            }
        }

        public AIDLServer getService() {
            return AIDLServer.this;
        }

    }
}
