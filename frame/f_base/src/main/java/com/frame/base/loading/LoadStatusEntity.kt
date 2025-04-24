package com.frame.base.loading

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:请求状态，请求数据为空 状态类
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
    //请求时回调回来-->发起请求时携带的参数 示例场景：发起请求时传递一个position ,如果请求失败时，可能需要把这个position回调给 activity/fragment 根据position做错误处理
    var intentData: Any? = null
)