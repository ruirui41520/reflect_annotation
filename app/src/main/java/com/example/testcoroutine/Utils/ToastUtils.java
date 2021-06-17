package com.example.testcoroutine.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ToastUtils {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private Context mContext;

    private Toast mTextToast;

    private Toast mImageToast;

    private int defaultGravity;

    private int defaultOffsetY;

    public ToastUtils(Context context) {
        mContext = context;
    }

    public void show(int resId) {
        show(resId, LENGTH_SHORT);
    }

    public void show(int resId, int duration) {
        show(mContext.getString(resId), duration);
    }

    public void show(CharSequence text) {
        show(text, LENGTH_SHORT);
    }

    public void show(CharSequence text, int duration) {
        show(text, duration, -1);
    }

    public void show(CharSequence text, int duration, int gravity) {
        if (mTextToast == null) {
            mTextToast = Toast.makeText(mContext, text, duration);
            defaultGravity = mTextToast.getGravity();
            defaultOffsetY = mTextToast.getYOffset();
        } else {
            mTextToast.setText(text);
            mTextToast.setDuration(duration);
        }

        if (gravity == -1) {
            mTextToast.setGravity(defaultGravity, 0, defaultOffsetY);
        } else {
            mTextToast.setGravity(gravity, 0, 0);
        }
        mTextToast.show();
    }

    public void show(View view) {
        show(view, LENGTH_SHORT);
    }

    public void show(View view, int duration) {
        if (mImageToast == null) {
            mImageToast = new Toast(mContext);
        }
        mImageToast.setView(view);
        mImageToast.setGravity(Gravity.CENTER, 0, 0);
        mImageToast.setDuration(duration);
        mImageToast.show();
    }

    public boolean isShowToast() {
        if (mTextToast == null || mTextToast.getView() == null) {
            return false;
        }
        return mTextToast.getView().isShown();
    }
}
