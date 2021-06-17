package com.example.testcoroutine.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.IOException;

public class PreferenceUtils {
    private final SharedPreferences preference;
    private final Context context;
    private String tokenCache;
    private long lastTimeOfRefreshTokenCache;
    private String refreshTokenCache;

    private static final String KEY_LAST_TIME_OF_REFRESH_TOKEN = "key.LastTimeOfRefreshToken";
    private static final String KEY_API_TOKEN = "key.ApiToken";
    private static final String KEY_REFRESH_TOKEN = "key.RefreshToken";
    private static final String KEY_API_TOKEN_EXPIRE_TIME = "key.ApiTokenExpireTime";


    public PreferenceUtils(Context context) {
        this.context = context;
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public synchronized String getApiToken() {
        if (tokenCache == null) {
            tokenCache = getCryptedString(KEY_API_TOKEN, null);
        }
        return tokenCache;
    }

    public synchronized String getRefreshToken() {
        if (refreshTokenCache == null) {
            refreshTokenCache = getCryptedString(KEY_REFRESH_TOKEN, null);
        }
        return refreshTokenCache;
    }

    public synchronized void setRefreshToken(String token) {
        refreshTokenCache = token;
        putCryptedString(KEY_REFRESH_TOKEN, token);
    }

    public synchronized long getLastTimeOfRefreshToken() {
        if (lastTimeOfRefreshTokenCache == 0L) {
            lastTimeOfRefreshTokenCache = preference.getLong(KEY_LAST_TIME_OF_REFRESH_TOKEN, 0L);
        }
        return lastTimeOfRefreshTokenCache;
    }

    public synchronized void setLastTimeOfRefreshToken(long time) {
        lastTimeOfRefreshTokenCache = time;
        preference.edit().putLong(KEY_LAST_TIME_OF_REFRESH_TOKEN, time).apply();
    }

    public long getApiTokenExpireTime() {
        return preference.getLong(KEY_API_TOKEN_EXPIRE_TIME, -1);
    }

    public void setApiTokenExpireTime(long expireTime) {
        preference.edit().putLong(KEY_API_TOKEN_EXPIRE_TIME, expireTime).apply();
    }

    private String getCryptedString(String key, String defValues) {
        String prefKey = key + "_";
        // Return defaultValue if preferences does not contain the key.
        if (!preference.contains(prefKey)) {
            return defValues;
        }
        // Get crypto encrypted base64 value from preferences.
        String encryptedBase64Value = preference.getString(prefKey, null);
        // Return null if value is null.
        if (encryptedBase64Value == null) {
            return null;
        }
        try {
            // Decrypt.
            byte[] decryptedBytes = Base64.decode(encryptedBase64Value, Base64.DEFAULT);
            return new String(decryptedBytes, "UTF-8");
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
        return defValues;
    }

    private void putCryptedString(String key, String value) {
        String prefKey = key + "_";
        if (value == null) {
            preference.edit().putString(prefKey, null).apply();
            return;
        }
        try {
            byte[] encryptedBytes = value.getBytes("UTF-8");
            String encryptedBase64Value = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
            preference.edit().putString(prefKey, encryptedBase64Value).apply();
        }catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }
}
