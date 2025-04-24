package com.tools.toast.ext

import com.hjq.toast.Toaster
import com.tools.toast.ToasterUtils


/**
 * @Author: james
 * @Date: 2023/9/6 14:59
 * @Description: 吐丝消息
 */
fun String?.toastLong() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showLong(this)
    }
}

fun CharSequence?.toastLong() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showLong(this)
    }
}

fun Int?.toastLong() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showLong(this)
    }
}

fun String?.toastShort() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showShort(this)
    }
}

fun CharSequence?.toastShort() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showShort(this)
    }
}

fun Int?.toastShort() {
    if (ToasterUtils.getInstance().checkInit()) {
        Toaster.showShort(this)
    }
}


fun String?.toast() {
    if (ToasterUtils.getInstance().checkInit()) {
        val params = ToasterUtils.getInstance().toasterParams()
        params.text = this
        Toaster.show(params)
    }
}

fun CharSequence?.toast() {
    if (ToasterUtils.getInstance().checkInit()) {
        val params = ToasterUtils.getInstance().toasterParams()
        params.text = this
        Toaster.show(params)
    }
}

fun Int?.toast() {
    if (ToasterUtils.getInstance().checkInit()) {
        val params = ToasterUtils.getInstance().toasterParams()
        params.text = this.toString()
        Toaster.show(params)
    }
}

