package com.example.testcoroutine.自定义View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.testcoroutine.DensityUtil;
import com.example.testcoroutine.R;

public class CustomDragView extends AppCompatImageView {
    private int screenWidth;
    private int screenHeight;
    private int width;
    private int height;
    private boolean isDrag=false;
    private float downX;
    private float downY;
    public boolean isDrag() {
        return isDrag;
    }

    public CustomDragView(Context context) {
        super(context);
    }

    public CustomDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        screenWidth = DensityUtil.getScreenWidth(getContext());
        screenHeight = DensityUtil.getScreenHeight(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    isDrag = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float widthDistance = event.getX() - downX;
                    float heightDistance = event.getY() - downY;
                    int l,r,t,b;
                    if (Math.abs(widthDistance) > 10 || Math.abs(heightDistance) > 10){
                        l = (int)(getLeft() + widthDistance);
                        r = l + width;
                        t = (int)(getTop() + heightDistance);
                        b = t + height;
                        if (l < 0){
                            l = 0;
                            r = l + width;
                        } else if (r > screenWidth){
                            r = screenWidth;
                            l = r - width;
                        }
                        if (t < 0){
                            t = 0;
                            b = t + height;
                        } else if (b > screenHeight){
                            b = screenHeight;
                            t = b - height;
                        }
                        this.layout(l,t,r,b);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }
}
