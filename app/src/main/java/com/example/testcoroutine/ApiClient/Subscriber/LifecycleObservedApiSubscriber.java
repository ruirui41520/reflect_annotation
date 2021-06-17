package com.example.testcoroutine.ApiClient.Subscriber;

import android.app.Activity;

import com.example.testcoroutine.ApiClient.ApiErrorResolver;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;
import com.example.testcoroutine.LogUtil.PL;
import com.example.testcoroutine.Utils.ViewLifecycleUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

public abstract class LifecycleObservedApiSubscriber<T> extends DefaultApiClientSubscriber<T> {

    private final WeakReference<Activity> activityRef;

    private final WeakReference<androidx.fragment.app.Fragment> supportFragmentRef;

    public abstract void onSuccessWithValidLifecycle(T t);

    public LifecycleObservedApiSubscriber(Activity activity) {
        super(activity);
        this.activityRef = new WeakReference<>(activity);
        this.supportFragmentRef = null;
    }

    public LifecycleObservedApiSubscriber(androidx.fragment.app.Fragment fragment) {
        super(fragment.getActivity());
        this.supportFragmentRef = new WeakReference<>(fragment);
        this.activityRef = null;
    }

    public WeakReference<androidx.fragment.app.Fragment> getSupportFragmentRef() {
        return supportFragmentRef;
    }

    @Override
    public final void onSuccess(T t) {
        if (isValidLifecycle()) {
            if (t instanceof ResultContainer) {
                ResultContainer rc = (ResultContainer) t;
                if (200 != rc.getStatus()) {
                    try {
                        onError(ApiErrorResolver.asXApiException(rc.getStatus()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }

            onSuccessWithValidLifecycle(t);
        }
    }

    @Override
    public final void onError(Throwable e) {
        if (isValidLifecycle()) {
            super.onError(e);
        }
    }

    private boolean isValidLifecycle() {
        boolean valid = true;
        if (activityRef != null) {
            valid = ViewLifecycleUtils.isValidLifecycle(activityRef.get());
        } else if (supportFragmentRef != null) {
            valid = ViewLifecycleUtils.isValidLifecycle(supportFragmentRef.get());
        }
        if(valid == false){
            PL.w("LifecycleObservedApiSubscriber" , "isValidLifecycle() - false");
        }

        return valid;
    }
}

