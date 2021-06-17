package com.example.testcoroutine.Utils;

import android.app.Activity;

public class ViewLifecycleUtils {

    private ViewLifecycleUtils() {
    }

    public static boolean isValidLifecycle(Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    public static boolean isValidLifecycle(androidx.fragment.app.Fragment fragment) {
        return fragment != null && fragment.getContext() != null && !fragment.isRemoving() && !fragment.isDetached() && fragment.isAdded();
    }
}
