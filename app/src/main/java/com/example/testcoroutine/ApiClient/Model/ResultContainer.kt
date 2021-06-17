package com.example.testcoroutine.ApiClient.Model

interface IResultContainer<T> {
    val status: Int
    val msg: String?
    val data: T?
}


data class ResultContainer<T>(
    override val status: Int,
    override val msg: String?,
    override val data: T?
) : IResultContainer<T>