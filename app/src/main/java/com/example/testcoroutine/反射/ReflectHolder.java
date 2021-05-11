package com.example.testcoroutine.反射;

import android.util.Log;

public class ReflectHolder {

    public String openValue;

    private String closeValue;

    private ReflectHolder() {}

    public ReflectHolder(String openValue, String closeValue) {
        this.openValue = openValue;
        this.closeValue = closeValue;
    }

    public void setOpenValue(String openValue) {
        this.openValue = openValue;
    }

    public void setCloseValue(String closeValue) {
        this.closeValue = closeValue;
    }

    public void printStringInstance(String str){
        Log.e("*********","printStringInstance: " + str);
    }

    public static void printString(String str){
        Log.e("*********","printString: " + str);
    }
}
