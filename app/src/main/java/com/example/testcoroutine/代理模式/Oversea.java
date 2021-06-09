package com.example.testcoroutine.代理模式;

import android.util.Log;

public class Oversea implements People {
    People domestic;

    public Oversea(People domestic) {
        this.domestic = domestic;
    }

    @Override
    public void buy() {
        Log.e("******test","我是国外代购： ");
        domestic.buy();
    }
}
