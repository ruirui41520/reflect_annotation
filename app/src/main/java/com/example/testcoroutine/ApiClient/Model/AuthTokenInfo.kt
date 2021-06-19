package com.example.testcoroutine.ApiClient.Model

data class AuthTokenInfo(
    val accessToken: String?,
    val expiresTime: Long?,
    val refreshToken: String?,
    val userId: String?
)