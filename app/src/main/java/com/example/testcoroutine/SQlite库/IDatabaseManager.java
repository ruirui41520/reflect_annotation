package com.example.testcoroutine.SQlite库;

import android.content.ContentValues;

import java.util.ArrayList;

public interface IDatabaseManager {
    public boolean insert(ContentValues mContentValues);
    public <T> ArrayList<T> query(String sql);
    public Object query(int id);
    public boolean update(ContentValues mContentValues);
    public boolean delete(int id);
    public void execSQL(String sql);
    public void closeDB();
}
