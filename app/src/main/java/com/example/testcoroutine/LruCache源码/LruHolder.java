package com.example.testcoroutine.LruCache源码;

public class LruHolder {
    private int mSize;
    private String mName;

    LruHolder(String name, int size) {
        mName = name;
        mSize = size;
    }

    public String getName() {
        return mName;
    }

    public int getSize() {
        return mSize;
    }
}
