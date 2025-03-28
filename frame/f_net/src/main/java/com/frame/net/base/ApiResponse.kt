package com.frame.net.base

/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description: 实体类基类处理
 */
data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)