package com.frame.framework.ext

import com.blankj.utilcode.util.ToastUtils

/**
 * @Author: james
 * @Date: 2023/9/6 14:59
 * @Description: 吐丝消息
 */
fun String?.toastLong(){
    ToastUtils.showLong(this)
}

fun CharSequence?.toastLong(){
    ToastUtils.showLong(this)
}

fun Int.toastLong(){
    ToastUtils.showLong(this)
}

fun String?.toastShort(){
    ToastUtils.showShort(this)
}

fun CharSequence?.toastShort(){
    ToastUtils.showShort(this)
}

fun Int.toastShort(){
    ToastUtils.showShort(this)
}