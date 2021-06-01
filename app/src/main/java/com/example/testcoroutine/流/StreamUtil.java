package com.example.testcoroutine.流;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.example.testcoroutine.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_MUSIC;
import static com.example.testcoroutine.FileUtils.FLAG_FAILED;
import static com.example.testcoroutine.FileUtils.FLAG_SUCCESS;

public class StreamUtil {
    private static SharedPreferences.Editor editor;

    public static void printAllDir(Context context){
        // storage
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        System.out.println("getExternalStorageDirectory :" + filePath);
        // system
        filePath = Environment.getRootDirectory().getPath();
        System.out.println("getRootDirectory :" + filePath);
        //cache
        filePath = Environment.getDownloadCacheDirectory().getPath();
        System.out.println("getDownloadCacheDirectory :" + filePath);
        //storage/emulated/0/Music
        filePath = Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).getPath();
        System.out.println("getExternalStoragePublicDirectory :" + filePath);

        // data/data/包名/cache
        filePath = context.getCacheDir().getPath();
        System.out.println("getCacheDir :" + filePath);
        // data/data/包名/files
        filePath = context.getFilesDir().getPath();
        System.out.println("getFilesDir :" + filePath);
        // data/data/包名/
        filePath = context.getDataDir().getPath();
        System.out.println("getFilesDir :" + filePath);
    }

    public static void copyFileDemo(){
        File fromFile = new File("/sdcard/testDemo.txt");
        if (!fromFile.exists()){
            return;
        }
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/demos/file/toDemo.txt";
        if (FileUtils.CreateFile(filePath) == FLAG_FAILED){
            Log.e("test","create failed");
            return;
        }
        File toFile = new File(filePath);
        if (!toFile.exists()){
            return;
        }
        try {
            FileReader reader = new FileReader(fromFile);
            FileWriter writer = new FileWriter(toFile);
            EveryCopyByBuffer(reader,writer);
            EveryCopyByByte(reader,writer);
            printData(toFile);
            reader.close();
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void copyFileToInternalFile(Context context){
        File fromFile = new File("/sdcard/testDemo.txt");
        if (!fromFile.exists()){
            return;
        }
        String internalFilePath = context.getFilesDir().getPath() + "/demo/toDemo.txt";
        File toFile = new File(internalFilePath);
        if (toFile.exists())toFile.delete();
        if (FileUtils.CreateFile(internalFilePath) == FLAG_FAILED){
            return;
        }
        if (!toFile.exists())return;
        try {
            FileReader reader = new FileReader(fromFile);
            FileWriter writer = new FileWriter(toFile);
            EveryCopyByBuffer(reader,writer);
            reader.close();
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void copyFileToInternalCache(Context context){
        File fromFile = new File("/sdcard/testDemo.txt");
        if (!fromFile.exists()){
            return;
        }
        String internalCachePath = context.getCacheDir().getPath() + "/demo/toDemo.txt";
        File toFile = new File(internalCachePath);
        if (toFile.exists())toFile.delete();
        if (FileUtils.CreateFile(internalCachePath) == FLAG_FAILED){
            return;
        }
        if (!toFile.exists())return;
        try {
            FileReader reader = new FileReader(fromFile);
            FileWriter writer = new FileWriter(toFile);
            EveryCopyByBuffer(reader,writer);
            printData(toFile);
            reader.close();
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveStringBySharePreference(Context context,String key,String value){
        // data/data/包名/shared_prefs
        if (editor == null){
            SharedPreferences sp = context.getSharedPreferences("colin", Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        editor.putString(key,value).apply();
    }


    private static void EveryCopyByBuffer(FileReader fileReader, FileWriter fileWriter) {
        char[] buff = new char[1024];
        int len = 0;
        try {
            while ((len = fileReader.read(buff)) != -1) {
                fileWriter.write(buff, 0, len);
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void EveryCopyByByte(FileReader fileReader, FileWriter fileWriter) throws IOException {
        int ch = 0;
        while ((ch = fileReader.read()) != -1) {
            fileWriter.write(ch);
        }
        fileWriter.flush();
    }

    private static void printData(File file){
        try {
            FileInputStream stream = new FileInputStream(file);
            int cha = 0;
//            while ((cha = stream.read()) != -1){
//                System.out.println((char)cha);
//            }
            byte[] buffer = new byte[1024];
            while ((cha = stream.read(buffer)) != -1){
                System.out.println(new String(buffer,0,cha));
            }
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
