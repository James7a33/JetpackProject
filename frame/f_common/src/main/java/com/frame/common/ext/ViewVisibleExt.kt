package com.frame.common.ext

import android.view.View

/**
 * View 显示
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * View 隐藏
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

/**
 * View 占位隐藏
 */
fun View?.inVisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * view 显示与隐藏
 * @param visible 如果为 true 该 View显示 否则隐藏
 */
fun View?.visible(visible: Boolean) {
    if (visible) {
        this.visible()
    } else {
        this.gone()
    }
}

/**
 * view 显示与占位隐藏
 * @param visible 如果为true 该View显示 否则占位隐藏
 */
fun View?.isInVisible(visible: Boolean) {
    if (visible) {
        this.inVisible()
    } else {
        this.visible()
    }
}

/**
 * view 显示
 * @param views 显示传入的 view集合
 */
fun visibleViews(vararg views: View?) {
    views.forEach {
        it?.visible()
    }
}

/**
 * view 隐藏
 * @param views 隐藏传入的 view集合
 */
fun goneViews(vararg views: View?) {
    views.forEach {
        it?.gone()
    }
}

/**
 * view 占位隐藏
 * @param views 隐藏传入的 view集合
 */
fun inVisibleViews(vararg views: View?) {
    views.forEach {
        it?.inVisible()
    }
}



