package com.http_lib

import java.io.BufferedOutputStream
import java.net.HttpURLConnection
import java.net.URL

class JsonHttpRequest : IHttpRequest {
    private var requestMethod: String = "GET"
    private var url: String? = null
    private var data: ByteArray? = null
    var callBackListener: CallBackListener? = null
    private lateinit var httpURLConnection: HttpURLConnection

    override fun setUrl(url: String) {
        this.url = url
    }

    override fun setRequestMethod(requestMethod: String) {
        this.requestMethod = requestMethod
    }

    override fun setData(data: ByteArray) {
        this.data = data
    }

    override fun setListener(callBackListener: CallBackListener) {
        this.callBackListener = callBackListener
    }

    override fun excute() {
        try {
            var url = URL(this.url)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 1000 * 6
            httpURLConnection.requestMethod = requestMethod
            httpURLConnection.useCaches = false
            httpURLConnection.instanceFollowRedirects = true
            httpURLConnection.readTimeout = 1000 * 3
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true
            httpURLConnection.setRequestProperty("Content-type", "application/json;charset=UTF-8")
            httpURLConnection.connect()
            var out = httpURLConnection.outputStream
            val bufferedOutputStream = BufferedOutputStream(out)
            bufferedOutputStream.write(data)
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = httpURLConnection.inputStream
                callBackListener?.let { it.onSuccess(inputStream) }
            } else {
                throw RuntimeException("请求失败")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("请求失败")
        } finally {
            httpURLConnection.disconnect()
        }
    }
}