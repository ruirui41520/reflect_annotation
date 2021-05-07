package com.example.testcoroutine.LruCache源码;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoaderUtil {
    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    ImageLoader loader = new ImageLoader();
    ImageView view;
    String loadedImageName;
    private String path = "http://www.baidu.com/img/bdlogo.png";
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SUCCESS:
                    byte[] picture_byte = (byte[]) msg.obj;
                    Bitmap image = BitmapFactory.decodeByteArray(picture_byte,0,picture_byte.length);
                    loader.addBitmap(loadedImageName, image);
                    view.setImageBitmap(image);
                    break;
                case FAIL:
                    break;
            }
        }
    };

    public void updateImage(String key, ImageView view){
        Bitmap image = loader.getBitmap(key);
        if (image == null){
            this.view = view;
            this.loadedImageName = key;
            getBitmapFromInternet();
            return;
        }
        view.setImageBitmap(image);
    }

    private void getBitmapFromInternet(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("test","loadFailed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    byte[] picture_bt = response.body().bytes();
                    Message message = handler.obtainMessage();
                    message.what = SUCCESS;
                    message.obj = picture_bt;
                    handler.sendMessage(message);
                } catch (NullPointerException e){
                    Log.e("test"," info: ");
                }
            }
        });
    }

}
