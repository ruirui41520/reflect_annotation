package com.example.testcoroutine.Utils;

import com.example.testcoroutine.ApiClient.Model.QuarkModel.EventMainView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonUtils {
    private static Gson gson;
    private JsonUtils(){
    }

    public static Gson getGson(){
        if (gson == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(EventMainView.class,new EventMainView.EventTypeAdapter());
            gson = builder.create();
        }
        return gson;
    }

    public static <T> T fromJson(String json, Type type){
        try {
            return getGson().fromJson(json,type);
        } catch (JsonSyntaxException e){
            return null;
        }
    }

    public static <T> String toJson(T obj){
        try {
            return getGson().toJson(obj,obj.getClass());
        }catch (JsonSyntaxException e){
            return null;
        }
    }
}
