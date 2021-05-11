package com.example.testcoroutine.序列化;

import android.os.Parcel;
import android.os.Parcelable;

public class PBook implements Parcelable {
    public int id;
    public String name;

    public PBook(Parcel in){
        id = in.readInt();
        name = in.readString();
    }

    public PBook(int id,String name){
        this.name = name;
        this.id = id;
    }

    public static final Parcelable.Creator<PBook> CREATOR = new Parcelable.Creator<PBook>(){

        @Override
        public PBook createFromParcel(Parcel source) {
            return new PBook(source);
        }

        @Override
        public PBook[] newArray(int size) {
            return new PBook[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
