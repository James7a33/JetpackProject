package com.frame.common.ext

import android.util.TypedValue
import com.frame.base.appContext

/**
 * @Author: james
 * @Date: 2023/9/6 16:05
 * @Description: 单位转换
 */

/**
 * 像素密度
 */
fun getDisplayMetrics() = appContext.resources.displayMetrics.density

/**
 * dp 转成为 px
 */
fun dp2px(dpValue: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpValue,
        appContext.resources.displayMetrics
    ).toInt()
}

val Float.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this, appContext.resources.displayMetrics)

val Int.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), appContext.resources.displayMetrics).toInt()

val Double.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), appContext.resources.displayMetrics).toInt()

/**
 * px 转成为 dp
 */
fun px2dp(pxValue: Float) = (pxValue / getDisplayMetrics() + 0.5f).toInt()

/**
 * sp转px
 */
fun sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spVal,
        appContext.resources.displayMetrics
    ).toInt()
}

/**
 * px转sp
 */
fun px2sp(pxVal: Float) = pxVal / appContext.resources.displayMetrics.scaledDensity


