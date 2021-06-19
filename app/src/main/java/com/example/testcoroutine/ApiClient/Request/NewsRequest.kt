package com.example.testcoroutine.ApiClient.Request

data class NewsRequest (
    val category:String,
    val count:Int,
    val page: Int
    )