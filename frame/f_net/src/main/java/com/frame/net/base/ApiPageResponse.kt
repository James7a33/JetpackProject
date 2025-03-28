package com.frame.net.base

import com.frame.net.NetConstants

/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description: 列表数据基类
 */
data class ApiPageResponse<T>(
    //Data
    var list: MutableList<T>,
    //当前页数
    var curPage: Int,
    //是否刷新
    var offset: Int,
    //是否无下一页数据
    var over: Boolean,
    //页数总量
    var pageCount: Int,
    //数量
    var size: Int,
    //总量
    var total: Int
) : BasePage<T>() {
    override fun getPageData() = list
    override fun isRefresh() = curPage == 1
    override fun isEmpty() = list.isEmpty()
    override fun hasMore() = total > NetConstants.DATA_LIMIT
}




