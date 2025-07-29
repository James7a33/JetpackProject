package com.frame.net.requset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frame.base.loading.LoadStatusEntity
import com.frame.base.loading.LoadingDialogEntity
import com.frame.base.loading.LoadingType
import com.frame.base.vm.BaseViewModel
import com.frame.net.NetConstants
import com.frame.net.error.code
import com.frame.net.error.msg
import com.tools.logger.logE
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * 普通封装一下 RxHttp请求
 * @receiver BaseViewModel
 * @param requestDslClass [@kotlin.ExtensionFunctionType] Function1<HttpRequestDsl, Unit>
 * @return Job? 可以根据这玩意 取消请求
 */
fun BaseViewModel.rxHttpRequest(requestDslClass: HttpRequestDsl.() -> Unit): Job? {
    val httpRequestDsl = HttpRequestDsl()
    requestDslClass(httpRequestDsl)
    return viewModelScope.launch {
        //发起请求时
        if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
            loadingChange.loading.value = LoadingDialogEntity(
                loadingType = httpRequestDsl.loadingType,
                loadingMessage = httpRequestDsl.loadingMessage,
                isShow = true,
                requestCode = httpRequestDsl.requestCode,
                coroutineScope = this
            )
        }
        runCatching {
            // 携程体方法执行工作 默认是在主线程中运行的，如果有耗时操作，需要自己切换线程 ，当然请求的话RxHttp内部做了处理的，不需要额外操作
            httpRequestDsl.onRequest.invoke(this)
        }.onSuccess {
            //请求完成 走到这里说明请求成功了 如果请求类型为LOADING_XML 那么通知Activity/Fragment展示success 界面
            if (httpRequestDsl.loadingType == LoadingType.LOADING_XML) {
                loadingChange.showSuccess.value = true
            }
            //结束loading
            if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
                loadingChange.loading.value = LoadingDialogEntity(
                    loadingType = httpRequestDsl.loadingType,
                    loadingMessage = httpRequestDsl.loadingMessage,
                    isShow = false,
                    requestCode = httpRequestDsl.requestCode
                )
            }
        }.onFailure {
            //手动取消异常，不用管 一般是手动关闭了请求弹窗，会跟着把这个请求取消了，然后会走错误异常，这里处理一下
            if (it is CancellationException) {
                return@onFailure
            }
            if (httpRequestDsl.onError == null) {
                //如果没有传递 onError参数 默认调用封装的逻辑
                if (it.code.toString() == NetConstants.EMPTY_CODE) {
                    //如果错误code 为 定义的 EMPTY_CODE（具体逻辑看 ResponseParser 的onParse方法）  说明是列表请求到了空数据
                    loadingChange.showEmpty.value =
                        LoadStatusEntity(
                            requestCode = httpRequestDsl.requestCode,
                            throwable = it,
                            errorCode = it.code,
                            errorMessage = it.msg,
                            isRefresh = httpRequestDsl.isRefreshRequest,
                            loadingType = httpRequestDsl.loadingType,
                            intentData = httpRequestDsl.intentData
                        )
                } else {
                    //请求失败时将错误日志打印一下 防止错哪里了都不晓得
                    it.printStackTrace()
                    "Request Error----> ${it.message}".logE()
                    //请求失败
                    loadingChange.showError.value =
                        LoadStatusEntity(
                            requestCode = httpRequestDsl.requestCode,
                            throwable = it,
                            errorCode = it.code,
                            errorMessage = it.msg,
                            isRefresh = httpRequestDsl.isRefreshRequest,
                            loadingType = httpRequestDsl.loadingType,
                            intentData = httpRequestDsl.intentData
                        )
                    //请求结束时
                    if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
                        loadingChange.loading.value =
                            LoadingDialogEntity(
                                loadingType = httpRequestDsl.loadingType,
                                loadingMessage = httpRequestDsl.loadingMessage,
                                isShow = false,
                                requestCode = httpRequestDsl.requestCode
                            )
                    }
                }
            } else {
                //请求失败时将错误日志打印一下 防止错哪里了都不晓得
                it.printStackTrace()
                "Request Error----> ${it.message}".logE()
                //传递了 onError 需要自己处理
                httpRequestDsl.onError?.invoke(it)
            }
        }

    }
}

/**
 * 普通封装一下 RxHttp请求
 * @receiver BaseViewModel
 * @param requestDslClass [@kotlin.ExtensionFunctionType] Function1<HttpRequestCallBackDsl<T>, Unit>
 * @return MutableLiveData<T>? 返回内部的 liveData
 */
fun <T> BaseViewModel.rxHttpRequestCallBack(requestDslClass: HttpRequestCallBackDsl<T>.() -> Unit): MutableLiveData<T>? {
    val httpRequestDsl = HttpRequestCallBackDsl<T>()
    httpRequestDsl.iAwaitLiveData = MutableLiveData()
    requestDslClass(httpRequestDsl)
    viewModelScope.launch {
        //发起请求时
        if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
            loadingChange.loading.value = LoadingDialogEntity(
                loadingType = httpRequestDsl.loadingType,
                loadingMessage = httpRequestDsl.loadingMessage,
                isShow = true,
                requestCode = httpRequestDsl.requestCode,
                coroutineScope = this
            )
        }
        runCatching {
            // 携程体方法执行工作 默认是在主线程中运行的，如果有耗时操作，需要切换线程 ，当然请求的话RxHttp内部做了处理的，不需要额外操作
            httpRequestDsl.onRequest.invoke(this)
        }.onSuccess {
            //请求完成 走到这里说明请求成功了 如果请求类型为LOADING_XML 那么通知Activity/Fragment展示success 界面
            if (httpRequestDsl.loadingType == LoadingType.LOADING_XML) {
                loadingChange.showSuccess.value = true
            }
            //结束loading
            if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
                loadingChange.loading.value = LoadingDialogEntity(
                    loadingType = httpRequestDsl.loadingType,
                    loadingMessage = httpRequestDsl.loadingMessage,
                    isShow = false,
                    requestCode = httpRequestDsl.requestCode,
                )
            }
            //请求成功且结束，将 iAwaitLiveData 置空
            httpRequestDsl.iAwaitLiveData = null
        }.onFailure {
            //请求失败，将 iAwaitLiveData 置空
            httpRequestDsl.iAwaitLiveData = null
            //手动取消异常，不用管 一般是手动关闭了请求弹窗，会跟着把这个请求取消了，然后会走错误异常，这里处理一下
            if (it is CancellationException) {
                return@onFailure
            }
            if (httpRequestDsl.onError == null) {
                //如果没有传递 onError参数 默认调用封装的逻辑
                if (it.code.toString() == NetConstants.EMPTY_CODE) {
                    //如果错误code 为 定义的 EMPTY_CODE（具体逻辑看 ResponseParser 的onParse方法）  说明是列表请求到了空数据
                    loadingChange.showEmpty.value =
                        LoadStatusEntity(
                            requestCode = httpRequestDsl.requestCode,
                            throwable = it,
                            errorCode = it.code,
                            errorMessage = it.msg,
                            isRefresh = httpRequestDsl.isRefreshRequest,
                            loadingType = httpRequestDsl.loadingType,
                            intentData = httpRequestDsl.intentData
                        )
                } else {
                    //请求失败时将错误日志打印一下 防止错哪里了都不晓得
                    it.printStackTrace()
                    "Request Error----> ${it.message}".logE()
                    //请求失败
                    loadingChange.showError.value =
                        LoadStatusEntity(
                            requestCode = httpRequestDsl.requestCode,
                            throwable = it,
                            errorCode = it.code,
                            errorMessage = it.msg,
                            isRefresh = httpRequestDsl.isRefreshRequest,
                            loadingType = httpRequestDsl.loadingType,
                            intentData = httpRequestDsl.intentData
                        )
                    //请求结束时
                    if (httpRequestDsl.loadingType != LoadingType.LOADING_NULL) {
                        loadingChange.loading.value =
                            LoadingDialogEntity(
                                loadingType = httpRequestDsl.loadingType,
                                loadingMessage = httpRequestDsl.loadingMessage,
                                isShow = false,
                                requestCode = httpRequestDsl.requestCode
                            )
                    }
                }
            } else {
                //请求失败时将错误日志打印一下 防止错哪里了都不晓得
                it.printStackTrace()
                "Request Error---->${it.message}".logE()
                //传递了 onError 需要自己处理
                httpRequestDsl.onError?.invoke(it)
            }
        }

    }
    return httpRequestDsl.iAwaitLiveData
}




