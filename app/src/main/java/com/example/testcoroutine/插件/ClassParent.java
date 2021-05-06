package com.example.testcoroutine.插件;

public class ClassParent implements ClassLoaderUtil.ClassInterface {
    static {
        System.out.println("load ClassParent");
    }
}
