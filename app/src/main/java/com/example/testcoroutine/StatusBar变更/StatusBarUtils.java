package com.example.testcoroutine.StatusBar变更;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtils {

    public static void setStatusBarByWindow(Activity activity, boolean visible){
        Window window = activity.getWindow();
        if (window != null) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            if (visible) {
                winParams.flags &= ~bits;
            } else {
                winParams.flags |= bits;
            }
            window.setAttributes(winParams);
        }
    }

    public static void setStatusBarVisibleByDecorView(Activity activity, boolean visible) {
        Window window = activity.getWindow();
        if (window != null) {
            if (visible) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }
    }

    // android:fitsSystemWindows="true"
    public static void setStatusBarTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
        }
    }

    public static void setStatusBarColor(Activity activity, int colorId) {
            Window window = activity.getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorId);
            }
    }

    public static void setStatusBarOverlay(Activity activity, boolean overlay) {
        Window window = activity.getWindow();
        if (window != null) {
            if (overlay) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    public static void setStatusBarTransparentDynamic(Activity activity, boolean overlay) {
            Window window = activity.getWindow();
            if (window != null) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                if (!overlay) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }
                window.setStatusBarColor(Color.TRANSPARENT);
            }
    }

}
