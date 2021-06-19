package com.example.testcoroutine.ApiClient.Request

class RefreshTokenRequest (
    val refreshToken: String,

    /**
     * deviceId of android (like Settings.Secure.ANDROID_ID)
     */
    val udid: String?
    )