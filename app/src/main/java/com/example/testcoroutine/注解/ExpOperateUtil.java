package com.example.testcoroutine.注解;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ExpOperateUtil {

    private void readSerializable(){
        ObjectInputStream object = null;
        try {
            FileInputStream fin = new FileInputStream(Environment.getExternalStorageState()+"/book.txt");
            object = new ObjectInputStream(fin);
            MBook book = (MBook)object.readObject();
            if (book != null){
                Log.d("TAG","book,id ="+book.id + " ,name="+book.name);
            } else {
                Log.e("TAG","book is null");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (object != null){
                    object.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void writeSerializable(){
        MBook book = new MBook();
        book.id = 1;
        book.name = "SBook";
        ObjectOutputStream object = null;
        try {
            FileOutputStream out = new FileOutputStream(Environment.getExternalStorageState() + "/book.txt");
            object = new ObjectOutputStream(out);
            object.writeObject(book);
            object.flush();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (object != null){
                    object.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Intent writeParcelable(){
        PBook tony = new PBook(1,"tony");
        PBook king = new PBook(2,"king");
        ArrayList<PBook> books = new ArrayList<>();
        books.add(tony);
        books.add(king);
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("PBook", books);
        return intent;
    }

    private void readParcelable(Intent intent){
        if (intent != null){
            ArrayList<PBook> books = intent.getParcelableArrayListExtra("PBook");
            if (books != null){
                for (PBook book: books){
                    Log.e("TAG","readParcelable, id=" + book.id + ", name=" + book.name);
                }
            }
        }
    }
}
