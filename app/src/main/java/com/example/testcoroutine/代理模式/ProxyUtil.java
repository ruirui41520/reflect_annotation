package com.example.testcoroutine.代理模式;

import java.lang.reflect.Proxy;

public class ProxyUtil {
    public static void buyUseStatic(){
        People domestic = new Domestic();
        People oversea = new Oversea(domestic);
        oversea.buy();
    }

    public static void buyUse(){
        People domestic = new Domestic();
        DynamicProxy dynamicProxy = new DynamicProxy(domestic);
        ClassLoader loader = domestic.getClass().getClassLoader();
        People overSea = (People) Proxy.newProxyInstance(loader, new Class[]{People.class}, dynamicProxy);
        overSea.buy();
    }
}
