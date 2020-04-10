package com.http_lib

import java.io.InputStream

/**
 * 接收结果回调 ,内部框架使用
 */
interface CallBackListener {
    fun onSuccess(inputStream: InputStream)
    fun onFailed()

}