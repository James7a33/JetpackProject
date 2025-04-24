package com.frame.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @Author: james
 * @Date: 2023/7/27 10:54
 * @Description:
 */
class CustomViewPager : ViewPager {
    // 是否滑动，用来标记
    private var isCanScroll = true

    fun setCanScroll(canScroll: Boolean) {
        isCanScroll = canScroll
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isCanScroll && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isCanScroll && super.onTouchEvent(ev)
    }
}