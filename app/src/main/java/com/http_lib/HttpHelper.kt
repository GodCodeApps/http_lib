package com.http_lib

object HttpHelper {
    fun <T,N>sendRequest(requestMethod:String,url:String,requestData:T,response:Class<N>, iJsonDataListener:IJsonDataListener<N>) {
        val iHttpRequest = JsonHttpRequest() as IHttpRequest
        val iCallBackListener = JsonCallBackListener<N>(response, iJsonDataListener) as CallBackListener
        val httpTask = HttpTask<T>(iHttpRequest, iCallBackListener,requestMethod, url, requestData)
        ThreadManager.getInstance().addTask(httpTask)
    }
    fun <T,N>post(url:String,requestData:T,response:Class<N>, iJsonDataListener:IJsonDataListener<N>) {
        sendRequest("POST",url,requestData,response,iJsonDataListener)
    }
    fun <T,N>get(url:String,requestData:T,response:Class<N>, iJsonDataListener:IJsonDataListener<N>) {
        sendRequest("GET",url,requestData,response,iJsonDataListener)
    }
}