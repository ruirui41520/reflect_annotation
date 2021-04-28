package com.example.zdd_viewinjector;

import android.app.Activity;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class ViewFinder {
    private static final ActivityProvider PROVIDER_ACTIVITY = new ActivityProvider();
    private static final ViewProvider PROVIDER_VIEW = new ViewProvider();
    private static final Map<String, Finder> INJECTOR_MAP = new HashMap<>();

    public static void inject(Activity activity){
        inject(activity,activity,PROVIDER_ACTIVITY);
    }

    public static void inject(View view){
        inject(view,view);
    }

    public static void inject(View host, View view){
        // for fragment
        inject(host,view,PROVIDER_VIEW);
    }

    @SuppressWarnings("unchecked")
    public static void inject(Object host, Object source, Provider provider){
        String className = host.getClass().getName();
        try {
            Finder finder = INJECTOR_MAP.get(className);
            if (finder == null){
                Class<?> injectorClass = Class.forName(className + "$$Finder");
                finder =  (Finder)injectorClass.newInstance();
                INJECTOR_MAP.put(className, finder);
            }
            finder.inject(host, source, provider);
        }catch (Exception e){
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }
}
