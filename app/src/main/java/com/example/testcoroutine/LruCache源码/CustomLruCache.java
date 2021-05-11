package com.example.testcoroutine.LruCache源码;

import android.util.Log;
import android.util.LruCache;

public class CustomLruCache extends LruCache<String,LruHolder> {
    public CustomLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, LruHolder value) {
        return value.getSize();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, LruHolder oldValue, LruHolder newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if (oldValue != null) {
            Log.e("CustomLruCache", "remove=" + oldValue.getName());
        }
        if (newValue != null) {
            Log.e("CustomLruCache", "add=" + newValue.getName());
        }
    }
}
