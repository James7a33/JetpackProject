package com.frame.base.ext

import android.app.ActivityManager
import android.content.Context
import com.frame.base.appContext

/**
 * @Author: james
 * @Date: 2023/9/26 17:30
 * @Description:
 */
/**
 * 获取当前进程的名称，默认进程名称是包名
 */
val currentProcessName: String?
    get() {
        val pid = android.os.Process.myPid()
        val mActivityManager = appContext.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }


