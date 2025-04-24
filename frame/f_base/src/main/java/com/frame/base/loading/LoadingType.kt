package com.frame.base.loading

import androidx.annotation.IntDef

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:自定义 LoadingType 类型
 */
@IntDef(
    /**
     * 请求时不需要Loading
     */
    LoadingType.LOADING_NULL,
    /**
     * 请求时弹出 通用Dialog弹窗Loading
     */
    LoadingType.LOADING_DIALOG,
    /**
     * 请求时 界面 Loading Error Empty
     */
    LoadingType.LOADING_XML,
    /**
     * 请求时弹出 Loading-自定义 在 fragment/activity 使用时需要重写 showCustomLoading dismissCustomLoading 方法
     */
    LoadingType.LOADING_CUSTOM
)
@Retention(AnnotationRetention.SOURCE)
annotation class LoadingType {
    companion object {
        //请求时不需要Loading
        const val LOADING_NULL = 0

        //请求时弹出 通用Dialog弹窗Loading
        const val LOADING_DIALOG = 1

        //请求时 界面 Loading Error Empty
        const val LOADING_XML = 2

        //请求时弹出 Loading-自定义 在 fragment/activity 使用时需要重写 showCustomLoading dismissCustomLoading 方法
        const val LOADING_CUSTOM = 3
    }
}