package com.example.testcoroutine.SQlite库;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public abstract class DataBaseManager<T> implements DataBaseOpenHelper.SQLiteDataTable, IDatabaseManager {
    private SQLiteDatabase mSqLiteDatabase;

    public DataBaseManager(Context context){
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getDatabaseOpenHelper(context);
        dataBaseOpenHelper.setOnSQLiteDataTable(this);
        dataBaseOpenHelper.setWriteAheadLoggingEnabled(true);
        mSqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
        try {
        } catch (Exception e){
            Log.e("*****ee","e.printStackTrace() : " );
            e.printStackTrace();
        }
    }

    @Override
    public boolean insert(ContentValues mContentValues) {
        mSqLiteDatabase.beginTransaction();
        try {
            long rowId = mSqLiteDatabase.insertOrThrow(getTableName(),null, mContentValues);
            mSqLiteDatabase.setTransactionSuccessful();
            return rowId != -1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    @Override
    public <T> ArrayList<T> query(String sql) {
        ArrayList<T> list = new ArrayList<>();
        Cursor cursor = mSqLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()){
            T mObject = (T) getResultFromCursor(cursor);
            list.add(mObject);
        }
        cursor.close();
        return list;
    }

    @Override
    public Object query(int id) {
        Cursor mCursor = mSqLiteDatabase.query(getTableName(),null,getPrimaryKeyID() + "=?",new String[]{String.valueOf(id)},null,null,null);
        return getResultFromCursor(mCursor);
    }

    @Override
    public boolean update(ContentValues mContentValues) {
        mSqLiteDatabase.beginTransaction();
        try {
            int rows = mSqLiteDatabase.update(getTableName(),mContentValues,getPrimaryKeyID() + "=?",new String[]{String.valueOf(mContentValues.get(getPrimaryKeyID()))});
            mSqLiteDatabase.setTransactionSuccessful();
            return rows > 0;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        mSqLiteDatabase.beginTransaction();
        try {
            int rows = mSqLiteDatabase.delete(getTableName(),getPrimaryKeyID() + "=?",new String[]{String.valueOf(id)});
            mSqLiteDatabase.setTransactionSuccessful();
            return rows > 0;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    @Override
    public void execSQL(String sql) {
    }

    @Override
    public void closeDB() {
        mSqLiteDatabase.close();
    }


    /**
     * 从子类获取表名
     * @return String 表的名称
     */
    public abstract String getTableName();
    /**
     * 获取表的主键名称
     * @return String 主键名
     */
    public abstract String getPrimaryKeyID();
    /**
     * 使用Cursor转换对象
     * @param mCursor 需要转换的Cursor对象
     * @return T 创建的对象
     */
    public abstract T getResultFromCursor(Cursor mCursor);

}
