package com.frame.base.loading

import com.main.res.R
import com.frame.base.appContext
import kotlinx.coroutines.CoroutineScope

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:自定义 LoadingDialogEntity 实体类
 */
data class LoadingDialogEntity(
    @LoadingType
    var loadingType: Int = LoadingType.LOADING_NULL, //加载类型
    var loadingMessage: String = appContext.getString(R.string.loading_network), //加载提示信息
    var isShow: Boolean = false, //是否显示
    var requestCode: String = "mmp", //请求码
    var coroutineScope: CoroutineScope? = null //请求绑定的作用域
)