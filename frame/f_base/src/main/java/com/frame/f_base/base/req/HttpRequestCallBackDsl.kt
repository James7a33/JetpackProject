package com.frame.f_base.base.req

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope

/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description:
 */
class HttpRequestCallBackDsl<T> {
    /**
     * 请求工作 在这里执行网络接口请求，然后回调成功数据
     */
    var onRequest: suspend CoroutineScope.() -> Unit = {}

    /**
     * 错误回调，默认为null 如果你传递了他 那么就代表你请求失败的逻辑你自己处理
     */
    var onError: ((Throwable) -> Unit)? = null

    /**
     * 目前这个只有在 loadingType != LOADING_NULL 的时候才有用 不然的话都不用传他
     */
    var loadingMessage: String = ""

    /**
     * 请求时loading类型 默认请求时不显示loading
     */
    @LoadingType
    var loadingType = LoadingType.LOADING_NULL

    /**
     * 请求 code 请求错误时 需要根据该字段去判断到底是哪个请求做相关处理 可以用URL去标记
     */
    var requestCode: String = "mmp"

    /**
     * 是否是刷新请求 做列表分页功能使用 一般请求用不到的
     */
    var isRefreshRequest: Boolean = false

    /**
     * 请求时回调给发起请求时携带的参数 示例场景：发起请求时传递一个 position ,如果请求失败时，可能需要把这个position回调给 activity/fragment 根据position做错误处理
     */
    var intentData: Any? = null

    /**
     * 请求成功 需要发射的数据
     */
    var iAwaitLiveData: MutableLiveData<T>? = null
}
