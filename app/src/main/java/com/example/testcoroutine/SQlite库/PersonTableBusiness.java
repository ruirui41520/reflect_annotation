package com.example.testcoroutine.SQlite库;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class PersonTableBusiness extends DataBaseManager<Person> {
    //表的名称
    private static final String PERSON_TABLE_NAME = "person";
    private static final String PERSON_FIELD_ID = "id";
    private static final String PERSON_FIELD_PERSON_NAME = "name";
    private static final String PERSON_FIELD_EMAIL = "email";
    private static final String PERSON_FIELD_HEIGHT = "height";


    private static final int PRIMARY_KEY_IS_NULL = -1;

    public PersonTableBusiness(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return PERSON_TABLE_NAME;
    }

    @Override
    public String getPrimaryKeyID() {
        return PERSON_FIELD_ID;
    }

    @Override
    public Person getResultFromCursor(Cursor mCursor) {
        Person person = new Person();
        person.setId(mCursor.getInt(mCursor.getColumnIndex(PERSON_FIELD_ID)));
        person.setName(mCursor.getString(mCursor.getColumnIndex(PERSON_FIELD_PERSON_NAME)));
        person.setEmail(mCursor.getString(mCursor.getColumnIndex(PERSON_FIELD_EMAIL)));
        person.setHeight(mCursor.getFloat(mCursor.getColumnIndex(PERSON_FIELD_HEIGHT)));
        return person;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_CREATE_PERSON_TABLE = "CREATE TABLE "
                + PERSON_TABLE_NAME + " (" + "" + PERSON_FIELD_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ""
                + PERSON_FIELD_PERSON_NAME + " VARCHAR(20) NOT NULL," + ""
                + PERSON_FIELD_EMAIL + " VARCHAR(20) NOT NULL," + ""
                + PERSON_FIELD_HEIGHT + " FLOAT(10) NOT NULL" + ")";
        Log.e("*****Database Table", DATABASE_CREATE_PERSON_TABLE);
        mSqLiteDatabase.execSQL(DATABASE_CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mSqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
    }

    public void deletePersonById(int id) {
        delete(id);
    }

    public void updatePerson(Person person) {
        update(createParameter(person));
    }


    public void updatePerson(int id, String name, String email, float height) {
        update(createParameter(id, name, email, height));
    }

    public void updatePersonList(ArrayList<Person> personArrayList) {
        for (Person person : personArrayList) {
            update(createParameter(person));
        }
    }

    public void insertPersonObject(Person person) {
        insert(createParameter(person));
    }

    public void insertPerson(String name, String email, float height) {
        ContentValues mContentValues = createParameter(PRIMARY_KEY_IS_NULL, name, email, height);
        insert(mContentValues);
    }

    public void insertListPerson(ArrayList<Person> mPersons) {
        for (Person mPerson : mPersons) {
            insert(createParameter(mPerson));
        }
    }

    public ArrayList<Person> queryAllPersons() {
        String sql = "select * from " + PERSON_TABLE_NAME;
        return query(sql);
    }


    public Person queryPersonById(int id){
        return (Person) query(id);
    }

    public ContentValues createParameter(Person person) {
        ContentValues contentValues = new ContentValues();
        if (person.getId() != PRIMARY_KEY_IS_NULL) {
            contentValues.put(PERSON_FIELD_ID, person.getId());
        }
        contentValues.put(PERSON_TABLE_NAME, person.getName());
        contentValues.put(PERSON_FIELD_EMAIL, person.getEmail());
        contentValues.put(PERSON_FIELD_HEIGHT, person.getHeight());
        return contentValues;
    }

    public ContentValues createParameter(int id, String name, String email, float height) {
        ContentValues mValues = new ContentValues();
        if (id != PRIMARY_KEY_IS_NULL) {
            mValues.put(PERSON_FIELD_ID, id);
        }
        mValues.put(PERSON_FIELD_PERSON_NAME, name);
        mValues.put(PERSON_FIELD_EMAIL, email);
        mValues.put(PERSON_FIELD_HEIGHT, height);
        return mValues;
    }

}
