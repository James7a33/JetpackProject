package com.frame.framework.widget

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.noober.background.BackgroundLibrary
import com.frame.framework.ext.addActivity
import com.frame.framework.ext.removeActivity
import com.tools.logger.logD

/**
 * @Author: james
 * @Date: 2024/3/18 17:06
 * @Description:Activity 管理器
 */
class ActivityLifecycleCallback : Application.ActivityLifecycleCallbacks, AppFrontBackListener {

    private val tag: String = this@ActivityLifecycleCallback.javaClass.simpleName
    // 新增应用状态标记
    companion object {
        //true 应用在前台 false 应用在后台
        @Volatile
        var isAppInForeground = false
            private set
    }

    /**
     * 打开的Activity数量统计
     */
    private var activityStartCount = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(activity)
        activity.javaClass.simpleName.logD(tag)
        addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        activity.javaClass.simpleName.logD(tag)
        activityStartCount++
        if (activityStartCount == 1) {
            onFront(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        activity.javaClass.simpleName.logD(tag)
    }

    override fun onActivityPaused(activity: Activity) {
        activity.javaClass.simpleName.logD(tag)
    }

    override fun onActivityStopped(activity: Activity) {
        activity.javaClass.simpleName.logD(tag)
        activityStartCount--
        if (activityStartCount == 0) {
            onBack(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        activity.javaClass.simpleName.logD(tag)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activity.javaClass.simpleName.logD(tag)
        removeActivity(activity)
    }

    override fun onFront(activity: Activity) {
        "${activity.javaClass.simpleName}  应用前台".logD(tag)
        isAppInForeground = true
        if (activity is AppFrontBackListener) {
            activity.onFront(activity)
        }
    }

    override fun onBack(activity: Activity) {
        "${activity.javaClass.simpleName}  应用后台".logD(tag)
        isAppInForeground = false
        if (activity is AppFrontBackListener) {
            activity.onBack(activity)
        }
    }
}

/**
 * App状态监听
 */
interface AppFrontBackListener {
    /**
     * 前台
     */
    fun onFront(activity: Activity)

    /**
     * 后台
     */
    fun onBack(activity: Activity)
}