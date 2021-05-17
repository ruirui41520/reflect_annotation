package com.example.testcoroutine.Recyclerview使用;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
public @interface RecyclerMarket {
    int layoutId();
    Class<? extends RecyclerView.ViewHolder> viewHolderClass();
}
