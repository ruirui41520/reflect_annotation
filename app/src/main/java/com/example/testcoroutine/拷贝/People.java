package com.example.testcoroutine.拷贝;

import androidx.annotation.NonNull;

public class People implements Cloneable{
    int age;
    Holder holder;

    @Override
    protected Object clone() {
        try {
            return super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static class Holder{
        int value;
    }
}
