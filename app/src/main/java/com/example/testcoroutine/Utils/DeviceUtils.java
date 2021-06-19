package com.example.testcoroutine.Utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceUtils {

    @Nullable
    public static String getDeviceId(@NonNull final Context context) {
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            return getSHA256Hash(androidId);
        }
        return null;
    }

    private static String getSHA256Hash(final @NonNull String message) {
        StringBuilder hexStringBuilder = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes());
            for (byte b : hash) {
                hexStringBuilder.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException exception) {
        }
        return hexStringBuilder.toString();
    }

}
