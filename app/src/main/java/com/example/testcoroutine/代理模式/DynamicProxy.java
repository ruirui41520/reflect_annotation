package com.example.testcoroutine.代理模式;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {
    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.print("海外动态代理调用方法：" + method.getName());
        Log.e("******test","method.getName(): " + method.getName());
        return method.invoke(object,args);
    }
}
