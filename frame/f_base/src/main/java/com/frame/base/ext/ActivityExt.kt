package com.frame.base.ext

import android.annotation.SuppressLint
import android.app.Activity
import java.util.LinkedList


/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: activity 管理器
 */

private val activityList = LinkedList<Activity>()

/*app当前显示的Activity*/
val currentActivity: Activity? get() = if (activityList.isEmpty()) null else activityList.last()

/**
 * 添加Activity入栈
 * @param activity Activity
 */
fun addActivity(activity: Activity) {
    activityList.add(activity)
}

/**
 * 关闭Activity出栈
 * @param activity Activity
 */
fun closeActivity(activity: Activity) {
    if (!activity.isFinishing) {
        activity.finish()
    }
    activityList.remove(activity)
}

/**
 * 从栈移除activity 不会finish
 * @param activity Activity
 */
fun removeActivity(activity: Activity) {
    activityList.remove(activity)
}

/**
 * 关闭Activity出栈
 * @param cls Class<*>
 */
fun closeActivity(cls: Class<*>) {
    if (activityList.isEmpty()) return
    val index = activityList.indexOfFirst { it.javaClass == cls }
    if (index == -1) return
    if (!activityList[index].isFinishing) {
        activityList[index].finish()
    }
    activityList.removeAt(index)
}

/**
 * 关闭所有的Activity 全部出栈
 */
@SuppressLint("SuspiciousIndentation")
fun closeAllActivity() {
    val iterator= activityList.iterator()
    while (iterator.hasNext()) {
        val activity = iterator.next()
//        if (activity.javaClass.simpleName != "MainActivity") {
            if (!activity.isFinishing) {
                activity.finish()
                iterator.remove()
            }
//        }
    }
}

/**
 * 关闭其他activity
 */
fun finishOtherActivity(clazz: Class<out Activity>) {
    val it = activityList.iterator()
    while (it.hasNext()) {
        val item = it.next()
        if (item::class.java != clazz) {
            it.remove()
            item.finish()
        }
    }
}