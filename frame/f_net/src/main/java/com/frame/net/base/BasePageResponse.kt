package com.frame.net.base

/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description: 分也帮助类
 */
abstract class BasePage<T> {
    /**
     * 列表数据
     * @return ArrayList<T>
     */
    abstract fun getPageData(): List<T>

    /**
     * 是否是第一页数据
     */
    abstract fun isRefresh(): Boolean

    /**
     * 数据是否为空
     */
    abstract fun isEmpty(): Boolean

    /**
     * 是否还有更多数据
     */
    abstract fun hasMore(): Boolean

}