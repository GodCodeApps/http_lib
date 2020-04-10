package com.http_lib.model

class Request{
    constructor(mobile: String?, password: String?) {
        this.mobile = mobile
        this.password = password
    }

    var mobile: String?=null
    var password: String?=null

}