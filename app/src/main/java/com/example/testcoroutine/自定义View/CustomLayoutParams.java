package com.example.testcoroutine.自定义View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class CustomLayoutParams extends ViewGroup.LayoutParams {
    public int left = 0;
    public int top = 0;
    public CustomLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public CustomLayoutParams(int width, int height) {
        super(width, height);
    }

    public CustomLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }
}
