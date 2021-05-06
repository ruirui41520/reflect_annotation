package com.example.testcoroutine.插件;

import java.io.InputStream;

public class ClassChild extends ClassParent {
    public static final int sNumber = 2;
    static {
        System.out.println("load ClassChild");
    }
}
