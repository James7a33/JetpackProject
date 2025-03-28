package com.tools.glide.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Author: james
 * Created: 2024/8/19 15:34
 * Description: 获取屏幕宽和高度
 */
@SuppressLint("WrongConstant")
fun getAppScreenWidth(context: Context): Int {
    val wm = context.getSystemService("window") as WindowManager
    val point = Point()
    wm.defaultDisplay.getSize(point)
    return point.x
}