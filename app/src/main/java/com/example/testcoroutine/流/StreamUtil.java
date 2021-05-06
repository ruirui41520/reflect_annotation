package com.example.testcoroutine.流;

import android.os.Environment;
import android.util.Log;

import com.example.testcoroutine.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.testcoroutine.FileUtils.FLAG_FAILED;

public class StreamUtil {

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
