package com.frame.common

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.task.project.Project
import com.frame.base.BaseApp
import com.frame.base.ext.currentProcessName
import com.frame.common.ext.CommonTaskFactory
import com.frame.common.ext.InitComm
import com.frame.common.ext.InitNetWork
import com.frame.common.ext.InitSDK
import com.frame.common.vm.AppEventVm
import com.frame.common.vm.AppUserVm
import com.tools.logger.mvvmHelperLog


val appUserVM: AppUserVm by lazy { CommonApp.appUserVm }

val appEventVM: AppEventVm by lazy { CommonApp.appEventVm }

/**
 * @Author: james
 * @Date: 2023/7/26 17:09
 * @Description:
 */
open class CommonApp : BaseApp(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        /*全局用户信息vm*/
        lateinit var appUserVm: AppUserVm

        /*全局通知vm*/
        lateinit var appEventVm: AppEventVm
    }

    override fun onCreate() {
        super.onCreate()
        //打印日志开关，框架是否打印请求日志、输出Log日志 默认为 true 打印数据
        mvvmHelperLog = true

        mAppViewModelStore = ViewModelStore()
        val processName = currentProcessName
        if (currentProcessName == packageName) {
            // 主进程初始化
            onMainProcessInit()
            appUserVm = getAppViewModelProvider()[AppUserVm::class.java]
            appEventVm = getAppViewModelProvider()[AppEventVm::class.java]
        } else {
            // 其他进程初始化
            processName?.let { onOtherProcessInit(it) }
        }
    }

    /**
     * @description  代码的初始化请不要放在onCreate直接操作，按照下面新建异步方法
     */
    private fun onMainProcessInit() {
        AnchorsManager.getInstance()
            .debuggable(BuildConfig.DEBUG)
            .addAnchor(
                InitNetWork.TASK_ID,
                InitSDK.TASK_ID,
                InitComm.TASK_ID,
            ).start(
                Project.Builder("CommonApp", CommonTaskFactory())
                    .add(InitNetWork.TASK_ID)
                    .add(InitSDK.TASK_ID)
                    .add(InitComm.TASK_ID)
                    .build()
            )
    }

    /**
     * 其他进程初始化，[processName] 进程名
     */
    private fun onOtherProcessInit(processName: String) {

    }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore

    /**
     * 获取一个全局的ViewModel
     */
    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}
