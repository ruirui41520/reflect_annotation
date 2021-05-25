// IMyAidlInterface.aidl
package com.example.zdd_aidlserver;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

   void login(String userName,String password);
}
