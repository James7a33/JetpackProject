package com.frame.net

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * @Author: james
 * @Date: 2023/7/25 21:32
 * @Description: 网络请求基础配置
 */
object NetConstants {

    // TODO: 设置默认域名
    @DefaultDomain
    const val DEV_URL = BuildConfig.BASE_URL

    // 服务器请求成功的 Code值
    const val SUCCESS_CODE = 200

    // 服务器请求失败的 Code值
    const val EMPTY_CODE = "99999"

    // 分页数量
    const val DATA_LIMIT = 20
}