package com.example.testcoroutine.代理模式;

import android.util.Log;

public class Domestic implements People {
    @Override
    public void buy() {
        Log.e("******test","国内要买一个包");
    }
}
