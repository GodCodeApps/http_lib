package com.http_lib

interface IJsonDataListener<T> {
    fun onSuccess(t: T)
    fun onFailed()
}