package com.frame.common.ext

import android.content.Intent
import android.os.Bundle
import com.frame.base.appContext
import com.frame.base.ext.currentActivity

/**
 * @Author: james
 * @Date: 2024/3/18 15:05
 * @Description: activity 跳转
 */

/**
 * Intent 跳转
 * @param clz 目标 activity
 */
fun toStartActivity(clz: Class<*>) {
    val intent = Intent(currentActivity, clz)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    currentActivity?.startActivity(intent)
}

/**
 * Intent 跳转
 * @param clz 目标 activity
 * @param bundle 参数
 */
fun toStartActivity(clz: Class<*>, bundle: Bundle) {
    val intent = Intent(currentActivity, clz)
    intent.apply {
        putExtras(bundle)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    currentActivity?.startActivity(intent)
}

/**
 * Intent 跳转
 * @param clz 目标 activity
 * @param requestCode code
 */

fun toStartActivity(clz: Class<*>, requestCode: Int) {
    currentActivity?.startActivityForResult(Intent(currentActivity, clz), requestCode)
}

/**
 * Intent 跳转
 * @param clz 目标 activity
 * @param bundle 参数
 * @param requestCode code
 */
fun toStartActivity(clz: Class<*>, bundle: Bundle, requestCode: Int) {
    currentActivity?.startActivityForResult(Intent(appContext, clz).putExtras(bundle), requestCode)
}
