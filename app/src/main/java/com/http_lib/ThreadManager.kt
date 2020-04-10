package com.http_lib

import android.util.Log
import java.util.concurrent.*

/**
 * 队列管理类
 */
class ThreadManager {
    private var mQueue = LinkedBlockingQueue<Runnable>()
    private var failedDelayQueue = DelayQueue<HttpTask<*>>()
    private var threadPoolExecuter: ThreadPoolExecutor? = null

    constructor() {
        threadPoolExecuter = ThreadPoolExecutor(
            3,
            10,
            15,
            TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(4),
            RejectedExecutionHandler { r, executor ->
                addTask(r)
            }
        )
        threadPoolExecuter?.execute(runnable)
        threadPoolExecuter?.execute(failedRunnable)

    }

    companion object {
        fun getInstance(): ThreadManager {
            return ThreadManager()
        }
    }


    fun addTask(runnable: Runnable) {
        if (runnable == null) {
            return
        }
        try {
            mQueue.put(runnable)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun addFailedTask(httpTask: HttpTask<*>) {
        if (httpTask == null) {
            return
        }
        try {
            httpTask.setDelayTime(3000)
            failedDelayQueue.offer(httpTask)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    /**
     * 核心线程
     */
    var runnable: Runnable = Runnable {
        while (true) {
            try {
                val take = mQueue.take()
                threadPoolExecuter?.execute(take)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    var failedRunnable: Runnable = Runnable {
        while (true) {
            try {
                val take = failedDelayQueue.take()
                if (take.maxNum < 3) {
                    take.maxNum = take.maxNum + 1
                    threadPoolExecuter?.execute(take)
                    Log.e("MainActivity", "重试==${take.maxNum}")
                } else {
                    val jsonHttpRequest = take.iHttpRequest as JsonHttpRequest
                    jsonHttpRequest.callBackListener?.onFailed()
                    Log.e("MainActivity", "尽力了==${take.maxNum}")
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}