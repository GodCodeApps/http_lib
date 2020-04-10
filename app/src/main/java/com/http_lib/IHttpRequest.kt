package com.http_lib

interface IHttpRequest {
    fun setUrl(url: String)
    fun setRequestMethod(requestMethod: String)
    fun setData(data: ByteArray)
    fun setListener(callBackListener: CallBackListener)
    fun excute()
}