package com.frame.net.interceptor

import com.frame.net.base.ApiResponse
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * @Author: james
 * @Date: 2023/7/28 16:34
 * @Description: 自定义 token 过期拦截器
 */

class TokenOutInterceptor : Interceptor {

    private val gson: Gson by lazy { Gson() }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body != null && response.body!!.contentType() != null) {
            when (response.code) {
                503 -> response
                200 -> {
                    val mediaType = response.body!!.contentType()
                    if (mediaType!!.type.contains("image")) {
                        return response
                    }
                    val string = response.body!!.string()
                    val responseBody = ResponseBody.create(mediaType, string)
                    val apiResponse = gson.fromJson(string, ApiResponse::class.java)
                    when (apiResponse.code) {
                        //登录失效
                        10000, 10001, 10002, 10003 -> {
                            LiveEventBus.get("logout", Boolean::class.java).post(true)
                        }
                    }
                    response.newBuilder().body(responseBody).build()
                }
                else -> response
            }
        } else {
            response
        }
    }
}