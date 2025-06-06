package com.frame.common.ext

import com.alibaba.android.arouter.launcher.ARouter
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.project.Project
import com.frame.base.BuildConfig
import com.frame.base.appContext
import com.frame.base.loadsir.base.BaseEmptyCallback
import com.frame.base.loadsir.base.BaseErrorCallback
import com.frame.base.loadsir.base.BaseLoadingCallback
import com.frame.net.client.NetHttpClient
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig
import rxhttp.RxHttpPlugins
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/9/6 14:42
 * @Description: 同步初始化
 */

class CommonTaskFactory : Project.TaskFactory(TaskCreator())

class TaskCreator : TaskCreator {
    override fun createTask(taskName: String): Task {
        return when (taskName) {
            InitNetWork.TASK_ID -> InitNetWork()
            InitSDK.TASK_ID -> InitSDK()
            InitComm.TASK_ID -> InitComm()
            else -> InitComm()
        }
    }
}

/**
 * 初始化网络
 */
class InitNetWork : Task(TASK_ID, false) {
    companion object {
        const val TASK_ID = "1"
    }

    override fun run(name: String) {
        //传入自己的OKHttpClient 并添加了自己的拦截器
        RxHttpPlugins.init(NetHttpClient.getDefaultOkHttpClient().build())
    }
}


/**
 * 初始化sdk
 *
 */
class InitSDK : Task(TASK_ID, false) {
    companion object {
        const val TASK_ID = "2"
    }

    override fun run(name: String) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(appContext)
        MMKV.initialize(appContext)
    }
}


//初始化常用控件类
class InitComm : Task(TASK_ID, false) {
    companion object {
        const val TASK_ID = "3"
    }

    override fun run(name: String) {
        //拉新下载
        initSmartRefresh()
        //状态管理
        initAppStateCallBack()
        //XPopup 初始化
//        initDialog()
        //设置不跟随手机系统字体大小改变
        AutoSizeConfig.getInstance().isExcludeFontScale = true
    }
}


/**
 * 全局下拉刷新
 *
 */
private fun initSmartRefresh() {
    SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
        //设置 SmartRefreshLayout 通用配置
        layout.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        layout.setFooterTriggerRate(0.6f)
    }
    SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
        //设置 Head
        ClassicsHeader(context).apply {
            setAccentColor(getColorExt(Rs.color.black))
        }
    }
    SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
        //设置 Footer
        ClassicsFooter(context).apply {
            setAccentColor(getColorExt(Rs.color.black))
        }
    }
}

/**
 * 全局状态回调
 */
private fun initAppStateCallBack() {
    LoadSir.beginBuilder()
        .addCallback(BaseErrorCallback()) //添加各种状态页
        .addCallback(BaseEmptyCallback())
        .addCallback(BaseLoadingCallback())
        .setDefaultCallback(SuccessCallback::class.java)
        .commit()
}





