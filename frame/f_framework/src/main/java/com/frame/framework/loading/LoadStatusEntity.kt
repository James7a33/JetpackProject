package com.frame.framework.loading

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/4
 * 描述　: 请求失败，请求数据为空 状态类
 */
data class LoadStatusEntity(
    //请求码
    var requestCode: String,
    //失败异常
    var throwable: Throwable,
    //错误码
    var errorCode:Int,
    //错误消息
    var errorMessage: String,
    //是否是列表切刷新请求 - 与分页功能配套使用
    var isRefresh: Boolean = false,
    // 请求时 loading 类型
    @LoadingType
    var loadingType: Int = LoadingType.LOADING_NULL,
    var intentData: Any? = null //请求时回调回来-->发起请求时携带的参数 示例场景：发起请求时传递一个position ,如果请求失败时，可能需要把这个position回调给 activity/fragment 根据position做错误处理
)