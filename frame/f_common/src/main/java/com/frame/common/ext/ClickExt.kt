package com.frame.common.ext

import android.view.View
import com.frame.common.constant.Constants


/**
 * @Author: james
 * @Date: 2023/9/6 16:05
 * @Description: 防止重复点击
 */

/**
 * 设置防止重复点击事件
 * @param views 需要设置点击事件的view
 * @param interval 时间间隔 默认0.3秒 @{Constants.App.CLICK_TIME_INTERVAL} 默认时间
 * @param onClick 点击触发的方法
 */
fun setOnclickNoRepeat(vararg views: View?, interval: Long = Constants.App.CLICK_TIME_INTERVAL, onClick: (View) -> Unit) {
    views.forEach {
        it?.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击事件 默认0.3秒内不可重复点击
 * @param interval 时间间隔 默认0.3秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = Constants.App.CLICK_TIME_INTERVAL, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action.invoke(it)
    }
}

/**
 * 设置点击事件
 * @param views 需要设置点击事件的view
 * @param onClick 点击触发的方法
 */
fun setOnclick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            val currentTime = System.currentTimeMillis()
            if (lastClickTime != 0L && (currentTime - lastClickTime < Constants.App.CLICK_TIME_INTERVAL)) {
                return@setOnClickListener
            }
            lastClickTime = currentTime
            onClick.invoke(view)
        }
    }
}

/**
 * 判断两次点击的间隔，如果小于默认时间 0.3，则认为是多次无效点击
 * @return true
 */
fun View.isFastDoubleClick(): Boolean {
    val currentTime = System.currentTimeMillis()
    if (lastClickTime != 0L && (currentTime - lastClickTime < Constants.App.CLICK_TIME_INTERVAL)) {
        return false
    }
    lastClickTime = currentTime
    return true
}