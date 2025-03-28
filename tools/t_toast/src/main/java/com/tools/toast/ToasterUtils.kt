package com.tools.toast

import android.app.Application
import android.view.Gravity
import com.hjq.toast.ToastParams
import com.hjq.toast.ToastStrategy
import com.hjq.toast.Toaster
import com.hjq.toast.style.BlackToastStyle


/**
 * Author: James
 * Created: 2025/03/28 14:56
 * Description: 自定义Toast
 */
class ToasterUtils private constructor() {

    companion object {
        @Volatile
        private var instance: ToasterUtils? = null

        fun getInstance(): ToasterUtils {
            return instance ?: synchronized(this) {
                instance ?: ToasterUtils().also { instance = it }
            }
        }
    }

    /**
     * 初始化Toaster
     * @param application Application
     */
    fun init(application: Application) {
        Toaster.init(application)
    }

    /**
     * 自定义Toast样式
     */
    fun toasterParams(): ToastParams {
        Toaster.setGravity(Gravity.BOTTOM)
        Toaster.setStrategy(ToastStrategy(ToastStrategy.SHOW_STRATEGY_TYPE_QUEUE))
        val params = ToastParams()
        params.style = BlackToastStyle()
        return params
    }

    /**
     * 检测是否初始化
     */
    fun checkInit(): Boolean {
        if (!Toaster.isInit()) {
            throw NullPointerException("Toaster No init")
        }
        return true
    }
}