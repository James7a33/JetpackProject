package com.frame.common.ext

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.frame.base.ext.currentActivity


/**
 * @Author: james
 * @Date: 2023/9/6 16:05
 * @Description: arouter 跳转
 */

/**
 * ARouter 跳转
 * @param routerPath 路径
 */
fun toStartActivity(routerPath: String) {
    ARouter.getInstance().build(routerPath).navigation()
}

/**
 * ARouter 跳转
 * @param routerPath 路径
 * @param bundle 参数
 */
fun toStartActivity(routerPath: String, bundle: Bundle) {
    ARouter.getInstance().build(routerPath).with(bundle).navigation()
}

/**
 * ARouter 跳转
 * @param routerPath 路径
 * @param requestCode code
 */
fun toStartActivity(routerPath: String, requestCode: Int) {
    ARouter.getInstance().build(routerPath).navigation(currentActivity, requestCode)
}

/**
 * ARouter 跳转
 * @param routerPath 路径
 * @param bundle 参数
 * @param requestCode code
 */
fun toStartActivity(routerPath: String, bundle: Bundle, requestCode: Int) {
    ARouter.getInstance().build(routerPath).with(bundle).navigation(currentActivity, requestCode)
}

/**
 * ARouter 根据路径获取实例
 * @param routerPath 路径
 */
fun getPathInstance(routerPath:String): Any? {
    return ARouter.getInstance().build(routerPath).navigation();
}
