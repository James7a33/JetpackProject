package com.frame.framework.loading

import com.frame.framework.appContext
import kotlinx.coroutines.CoroutineScope
import com.frame.res.R as Rs
/**
 * 作者　: hegaojian
 * 时间　: 2020/11/3
 * 描述　:
 */
data class LoadingDialogEntity(
    @LoadingType
    var loadingType: Int = LoadingType.LOADING_NULL,
    var loadingMessage: String = appContext.getString(Rs.string.loading_network),
    var isShow: Boolean = false,
    var requestCode: String = "mmp",
    var coroutineScope: CoroutineScope? = null //请求绑定的作用域
)