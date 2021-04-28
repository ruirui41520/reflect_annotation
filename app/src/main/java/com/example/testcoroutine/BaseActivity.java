package com.example.testcoroutine;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyseAnnotation();
    }

    private void analyseAnnotation(){
        Class cls = this.getClass();
        for (; cls != Context.class; cls = cls.getSuperclass()){
            ContentView annotation = (ContentView) cls.getAnnotation(ContentView.class);
            if (annotation != null){
                try {
                    this.setContentView(annotation.value());
                }catch (RuntimeException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
