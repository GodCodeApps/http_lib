package com.http_lib

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class JsonCallBackListener<T> : CallBackListener {
    private var response: Class<T>
    private var iJsonDataListener: IJsonDataListener<T>
    private var handler = Handler(Looper.getMainLooper())

    constructor(response: Class<T>, iJsonDataListener: IJsonDataListener<T>) {
        this.response = response
        this.iJsonDataListener = iJsonDataListener
    }

    override fun onFailed() {
        handler.post {
            this.iJsonDataListener.onFailed()
        }
    }

    override fun onSuccess(inputStream: InputStream) {
        val content = getContent(inputStream)
        val t = Gson().fromJson(content, response)
        handler.post {
            this.iJsonDataListener.onSuccess(t)
        }
    }

    private fun getContent(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = null
        try {
            while (true) {
                line = bufferedReader.readLine()
                if (line != null) {
                    sb.append("$line/n")
                } else {
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString().replace("/n", "")
    }
}