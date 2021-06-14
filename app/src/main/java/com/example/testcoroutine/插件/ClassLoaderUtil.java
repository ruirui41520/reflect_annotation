package com.example.testcoroutine.插件;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class ClassLoaderUtil {
    private static String apkPath = "/sdcard/test.dex";
    public static void loadClass(Context context){
        try {
            Class<?> baseDexClassLoaderClazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoaderClazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            Class<?> dexPathListClazz = Class.forName("dalvik.system.DexPathList");
            Field dexElementField = dexPathListClazz.getDeclaredField("dexElements");
            dexElementField.setAccessible(true);

            /*
            dexPath: 需要加载的文件列表，文件可以是包含了 classes.dex 的 JAR/APK/ZIP，也可以直接使用 classes.dex 文件，多个文件用 “:” 分割
optimizedDirectory: 存放优化后的 dex，可以为空
libraryPath: 存放需要加载的 native 库的目录
parent: 父 ClassLoader
             */
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),null, context.getClassLoader());
            Object pluginPathList = pathListField.get(dexClassLoader);
            Object[] pluginElements = (Object[])dexElementField.get(pluginPathList);

            PathClassLoader pathClassLoader = (PathClassLoader)context.getClassLoader();
            Object hostPathList = pathListField.get(pathClassLoader);
            Object[] hostElements = (Object[])dexElementField.get(hostPathList);

            Object[] compoundElements = (Object[])Array.newInstance(hostElements.getClass().getComponentType(),pluginElements.length + hostElements.length);
            System.arraycopy(hostElements,0,compoundElements,0,hostElements.length);
            System.arraycopy(pluginElements,0,compoundElements,hostElements.length,pluginElements.length);

            dexElementField.set(hostPathList,compoundElements);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public interface ClassInterface {}

    public static void useStaticVariable(){
        Log.e("load Class","ClassChild.sNumber + " + ClassChild.sNumber);
    }

    private void launchClass(){
//        PathClassLoader classLoader = new PathClassLoader(path, getClassLoader());
        try {
//            Class<?> testClass = classLoader.loadClass("com.example.zdd_plugin.TestPlugin");
            Class<?> testClass = Class.forName("com.example.zdd_plugin.TestPlugin");
            Method sayHiMethod = testClass.getMethod("print");
            sayHiMethod.invoke(null);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
