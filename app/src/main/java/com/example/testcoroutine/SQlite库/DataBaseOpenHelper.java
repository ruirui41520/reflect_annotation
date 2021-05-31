package com.example.testcoroutine.SQlite库;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static DataBaseOpenHelper mDatabaseOpenHelper;
    private static final int DATABASE_VERSION = 1;
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "manager.db";

    public synchronized static DataBaseOpenHelper getDatabaseOpenHelper(Context context){
        if (mDatabaseOpenHelper == null){
            mDatabaseOpenHelper=  new DataBaseOpenHelper(context);
        }
        return mDatabaseOpenHelper;
    }

    public interface SQLiteDataTable{
        void onCreate(SQLiteDatabase db);
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    private SQLiteDataTable table;

    public void setOnSQLiteDataTable(SQLiteDataTable table) {
        this.table = table;
    }

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        table.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        table.onUpgrade(db,oldVersion,newVersion);
        onCreate(db);
    }
}
