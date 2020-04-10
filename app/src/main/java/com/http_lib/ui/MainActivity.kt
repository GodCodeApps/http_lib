package com.http_lib.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.http_lib.HttpHelper
import com.http_lib.IJsonDataListener
import com.http_lib.R
import com.http_lib.model.ReqUser
import com.http_lib.model.Request
import com.http_lib.model.Response
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener {
//            for (index in 1..2){
//                HttpHelper.post("http://120.79.11.33:8081/renren-api/zyf/api/login",
//                    Request("17665490253", "123456"),
//                    Response::class.java,
//                    object : IJsonDataListener<Response> {
//                        override fun onSuccess(t: Response) {
//                            if (t != null) {
//                                Log.e("MainActivity","成功===${index}")
//                            }
//                        }
//
//                        override fun onFailed() {
//                            Log.e("MainActivity","失败===${index}")
//
//                        }
//                    })
//            }
            HttpHelper.get("http://192.168.1.103:8081/renren-api/zyf/article/listByUserId",
                ReqUser(1),Response::class.java,
                object : IJsonDataListener<Response> {
                    override fun onSuccess(t: Response) {
                        if (t != null) {
                            Log.e("MainActivity","成功===")
                        }
                    }

                    override fun onFailed() {
                        Log.e("MainActivity","失败===")

                    }
                })
        }
    }
}
