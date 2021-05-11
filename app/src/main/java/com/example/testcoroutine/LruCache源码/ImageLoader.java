package com.example.testcoroutine.LruCache源码;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageLoader {
    private LruCache<String, Bitmap> lruCache;

    public ImageLoader(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory() / 1024;
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight() / 1024;
            }
        };
    }

    public void addBitmap(String key,Bitmap value){
        if (getBitmap(key) == null){
            lruCache.put(key,value);
        }
    }

    public Bitmap getBitmap(String key){
        return lruCache.get(key);
    }

}
