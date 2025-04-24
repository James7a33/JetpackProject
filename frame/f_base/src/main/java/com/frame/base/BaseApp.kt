package com.frame.base

import androidx.multidex.MultiDexApplication
import com.frame.base.widget.ActivityLifecycleCallback


/**
 * @Author: james
 * @Date: 2024/3/18 16:17
 * @Description:
 */

val appContext: MultiDexApplication by lazy { BaseApp.getInstance() }

open class BaseApp: MultiDexApplication() {

    companion object {

        private lateinit var instance: MultiDexApplication

        /**
         * 获得当前app运行的Application
         */
        @JvmStatic
        fun getInstance(): MultiDexApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
    }
}