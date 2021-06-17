package com.example.testcoroutine.ApiClient.NetWork;

import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

public class ServerConfig {

    public static final String TOKEN_HEADER_NAME = "X-Client-AccessToken";

    public static final String LINE_LIVE_ACCESS_TOKEN = "X-AccessToken";
    public static final String LINE_LIVE_PUSH_TOKEN = "X-PushToken";
    public static final String LINE_LIVE_PUSH_TYPE = "X-PushType";
    public static final String LINE_LIVE_PUSH_SETTING = "X-PushSetting";
    public static final String LINE_LIVE_TIMEZONE_ID = "X-TimeZoneId";
    public static final String LINE_LIVE_COUNTRY = "XCountry";
    public static final String LINE_LIVE_ADID = "XAdid";

    public static final String USER_AGENT_HEADER = "User-Agent";

    private ServerConfig() {
    }

    /**
     * Returns User Agent string for GamePortal server.
     */
    public static String getUserAgent() {
        return String.format(Locale.US, "CastService/%s Android/%s %s", "1.0", Build.VERSION.RELEASE, getNormalizedDeviceModel());
    }

    private static String getNormalizedDeviceModel() {
        if (TextUtils.isEmpty(Build.MODEL)) {
            return "";
        }

        return Build.MODEL.replaceAll(" ", "_");
    }
}
