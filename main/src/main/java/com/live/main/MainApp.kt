package com.live.main

import cat.ereza.customactivityoncrash.config.CaocConfig
import com.frame.common.CommonApp
import com.live.main.activity.CrashActivity
import com.live.main.activity.SplashActivity

/**
 * Author: james
 * Created: 2024/8/21 11:52
 * Description: 当前业务的Application
 */
open class MainApp : CommonApp() {

    override fun onCreate() {
        super.onCreate()
        initCrash()
    }

    /**
     * 全局异常捕获
     *
     */
    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
            .enabled(true)              //是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
            .showErrorDetails(true)     //是否必须显示包含错误详细信息的按钮 default: true
            .showRestartButton(true)    //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
            .logErrorOnRestart(true)    //是否必须重新堆栈堆栈跟踪 default: true
            .trackActivities(true)      //是否必须跟踪用户访问的活动及其生命周期调用 default: false
            .minTimeBetweenCrashesMs(500) //应用程序崩溃之间必须经过的时间 default: 3000
            .restartActivity(SplashActivity::class.java)     // 重启的activity
            .errorActivity(CrashActivity::class.java) //发生错误跳转的activity
            .apply()
    }

}