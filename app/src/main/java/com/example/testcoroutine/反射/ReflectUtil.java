package com.example.testcoroutine.反射;

import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    public static void createHolderClass(){
        Class ls = ReflectHolder.class;
        Constructor constructor;
        try {
            // 反射获取构造函数
            constructor = ls.getDeclaredConstructor(String.class,String.class);
            ReflectHolder holder = (ReflectHolder) constructor.newInstance("open","close");
            // 反射获取静态方法并调用类方法
            Method method = ls.getMethod("printString", String.class);
            method.invoke(ls,"openValue");

            // 反射获取对象方法并调用对象方法
            Method methodInstance = ls.getMethod("printStringInstance", String.class);
            methodInstance.invoke(holder,"printString");

            //反射获取变量
            Field field = ls.getField("openValue");
            field.setAccessible(true);
            Log.e("********","field : " + field.get(holder));
            Log.e("***********","value : " + holder.openValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
