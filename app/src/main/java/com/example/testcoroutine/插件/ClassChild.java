package com.example.testcoroutine.插件;

public class ClassChild extends ClassParent {
    public static final int sNumber = 2;
    static {
        System.out.println("load ClassChild");
    }
}
