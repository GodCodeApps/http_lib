package com.http_lib

import com.google.gson.Gson
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

/**
 * 请求对象线程
 */
class HttpTask<T> : Runnable, Delayed {
    private var delayTime: Long = 0
    var maxNum: Int = 0
    var iHttpRequest: IHttpRequest

    fun setDelayTime(delayTime: Long) {
        this.delayTime = delayTime + System.currentTimeMillis()
    }

    fun getDelayTime(): Long {
        return delayTime
    }

    override fun compareTo(other: Delayed?): Int {
        return 0

    }

    override fun getDelay(unit: TimeUnit): Long {
        return unit.convert(getDelayTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }


    constructor(
        iHttpRequest: IHttpRequest,
        callBackListener: CallBackListener,
        requestMethod:String,
        url: String,
        requestData: T
    ) {
        this.iHttpRequest = iHttpRequest
        this.iHttpRequest.setRequestMethod(requestMethod)
        this.iHttpRequest.setUrl(url)
        this.iHttpRequest.setListener(callBackListener)
        if (requestData != null) {
            var dataStr = Gson().toJson(requestData)
            try {
                val bytes = dataStr.toByteArray(charset("utf-8"))
                this.iHttpRequest.setData(bytes)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

        }
    }

    override fun run() {
        try {
            this.iHttpRequest.excute()
        } catch (e: Exception) {
            ThreadManager.getInstance().addFailedTask(this)
        }
    }
}