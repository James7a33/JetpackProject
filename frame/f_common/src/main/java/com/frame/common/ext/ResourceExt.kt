package com.frame.common.ext

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.frame.base.appContext
import com.frame.base.ext.currentActivity

/**
 * @Author: james
 * @Date: 2023/9/6 14:42
 * @Description: 资源文件
 */
fun getDrawableExt(id: Int): Drawable? =
    currentActivity?.let { ContextCompat.getDrawable(it, id) }
        ?: ContextCompat.getDrawable(appContext, id)


/**
 * 获取颜色
 * @param id 资源Id 如 R.color.red
 */
fun getColorExt(id: Int): Int =
    currentActivity?.let { ContextCompat.getColor(it, id) }
        ?: ContextCompat.getColor(appContext, id)

/**
 * 获取颜色
 * @param color 资源Id 如"#ffffff"
 */
fun getColorExt(color: String): Int = Color.parseColor(color)

/**
 * 获取字符串
 * @param id 资源Id 如 R.string.title
 */
fun getStringExt(id: Int) = currentActivity?.resources?.getString(id)
    ?: appContext.resources.getString(id)

/**
 * 获取字符串
 * @param id 资源Id 如 R.string.title
 * @param formatArgs 替换参数
 */
fun getStringExt(id: Int, formatArgs: Any) =
    currentActivity?.resources?.getString(id, formatArgs)
        ?: appContext.resources.getString(id, formatArgs)

/**
 * 获取字符串数组
 * @param id 资源Id 如 R.string.title
 */
fun getStringArrayExt(id: Int): Array<String> = currentActivity?.resources?.getStringArray(id)
    ?: appContext.resources.getStringArray(id)


/**
 * 获取Int 数组
 * @param id 资源Id 如 R.string.title
 */
fun getIntArrayExt(id: Int) = currentActivity?.resources?.getIntArray(id)
    ?: appContext.resources.getIntArray(id)

/**
 * 获取dp
 * @param id 资源Id 如 R.dimen.dp_1
 * @return float
 */
fun getDimensionExt(id: Int) = currentActivity?.resources?.getDimension(id)
    ?: appContext.resources.getDimension(id)

/**
 * 获取dp
 * @param id 资源Id 如 R.dimen.dp_1
 * @return int
 */
fun getDimensionPixelOffsetExt(id: Int) = currentActivity?.resources?.getDimensionPixelOffset(id)
    ?: appContext.resources.getDimensionPixelOffset(id)
