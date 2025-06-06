package com.frame.base.loadsir

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.frame.base.loadsir.base.BaseEmptyCallback
import com.frame.base.loadsir.base.BaseErrorCallback
import com.frame.base.loadsir.base.BaseLoadingCallback
import com.frame.base.view.BaseIView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir


/**
 * @Author: James
 * @Date: 2025/6/4 10:38
 * @Description:页面状态管理
 */
class LoadStateManager(
    private val context: Context,
    private val containerView: FrameLayout,
    private val loadingView: View? = null
) {
    private lateinit var loadService: LoadService<*>

    fun init(
        emptyCallback: Callback? = null,
        errorCallback: Callback? = null,
        loadingCallback: Callback? = null,
        networkCallback: Callback? = null,
        customCallbacks: List<Callback>? = null
    ) {
        val builder = LoadSir.beginBuilder()
            .addCallback(emptyCallback ?: BaseEmptyCallback())
            .addCallback(errorCallback ?: BaseErrorCallback())
            .addCallback(loadingCallback ?: BaseLoadingCallback())
            .addCallback(networkCallback ?: BaseErrorCallback())
            .setDefaultCallback(SuccessCallback::class.java)
        customCallbacks?.forEach { builder.addCallback(it) }

        loadService = builder.build().register(loadingView ?: containerView) {
            (context as? BaseIView)?.onLoadRetry()
        }
    }

    fun getLoadService(): LoadService<*> = loadService
}