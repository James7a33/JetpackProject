package com.frame.net.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @Author: james
 * @Date: 2023/8/1 17:32
 * @Description: json 解析
 */

val gson: Gson by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Gson() }

/**
 * json 转 实体类
 */
inline fun <reified T> String.toEntity(): T? {
    val type = object : TypeToken<T>() {}
    return try {
        gson.fromJson(this, type.type)
    } catch (e: Exception) {
        null
    }
}

/**
 * json 转 List<T>
 */
inline fun <reified T> String.toArrayEntity(): ArrayList<T>? {
    val type = object : TypeToken<ArrayList<T>>() {}
    return try {
        gson.fromJson(this, type.type)
    } catch (e: Exception) {
        null
    }
}

/**
 * 实体类 转 json
 */
fun Any?.toJsonStr(): String {
    return gson.toJson(this)
}
