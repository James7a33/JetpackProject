package com.frame.base.loading

import com.frame.base.appContext
import kotlinx.coroutines.CoroutineScope
import com.james.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:自定义 LoadingDialogEntity 实体类
 */
data class LoadingDialogEntity(
    @LoadingType
    var loadingType: Int = LoadingType.LOADING_NULL,
    var loadingMessage: String = appContext.getString(Rs.string.loading_network),
    var isShow: Boolean = false,
    var requestCode: String = "mmp",
    var coroutineScope: CoroutineScope? = null //请求绑定的作用域
)