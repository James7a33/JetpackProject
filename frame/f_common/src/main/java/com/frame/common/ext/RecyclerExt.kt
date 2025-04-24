package com.frame.common.ext

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * 纵向recyclerview
 * @receiver RecyclerView
 * @receiver canScrollVertically   是否竖向滚动
 * @receiver canScrollHorizontally 是否横向滚动
 * @return RecyclerView
 */
fun RecyclerView.vertical(
    canScrollVertically: Boolean = true,
    canScrollHorizontally: Boolean = false
): RecyclerView {
    layoutManager = object : LinearLayoutManager(this.context) {
        override fun canScrollVertically(): Boolean {
            return canScrollVertically
        }

        override fun canScrollHorizontally(): Boolean {
            return canScrollHorizontally
        }
    }.apply {
        orientation = RecyclerView.VERTICAL
    }
    return this
}

/**
 * 横向 recyclerview
 * @receiver RecyclerView
 * @receiver canScrollVertically   是否竖向滚动
 * @receiver canScrollHorizontally 是否横向滚动
 * @return RecyclerView
 */
fun RecyclerView.horizontal(
    canScrollVertically: Boolean = false,
    canScrollHorizontally: Boolean = true
): RecyclerView {
    layoutManager = object : LinearLayoutManager(this.context) {
        override fun canScrollVertically(): Boolean {
            return canScrollVertically
        }

        override fun canScrollHorizontally(): Boolean {
            return canScrollHorizontally
        }
    }.apply {
        orientation = RecyclerView.HORIZONTAL
    }
    return this
}

/**
 * grid recyclerview
 * @receiver RecyclerView
 * @receiver canScrollVertically   是否竖向滚动
 * @receiver canScrollHorizontally 是否横向滚动
 * @return RecyclerView
 */
fun RecyclerView.grid(
    count: Int,
    canScrollVertically: Boolean = false,
    canScrollHorizontally: Boolean = false
): RecyclerView {
    layoutManager = object : GridLayoutManager(this.context, count) {
        override fun canScrollVertically(): Boolean {
            return canScrollVertically
        }

        override fun canScrollHorizontally(): Boolean {
            return canScrollHorizontally
        }
    }
    return this
}

