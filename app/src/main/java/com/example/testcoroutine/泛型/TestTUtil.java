package com.example.testcoroutine.泛型;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.List;

public class TestTUtil {
    TClass<String> stringTClass = new TClass<>();
    public void testT(){
        stringTClass.update("abc");
    }

    // 只写
    public static void writeListWithS(List<? super Integer> list){
        list.add(1);
        list.add(2);
        list.add(3);
    }

    // 只读
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void readListWithE(List<? extends Integer> list){
        list.forEach((e)->{
            Log.e("*********","value is : " + e.intValue());
        });
    }

}
