package com.frame.common.ext

import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.block
import com.effective.android.anchors.debuggable
import com.effective.android.anchors.graphics
import com.effective.android.anchors.startUp
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.lock.LockableAnchor
import com.effective.android.anchors.task.project.Project
import com.effective.android.anchors.taskFactory
import com.frame.base.BuildConfig
import com.frame.base.appContext
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV


/**
 * @Author: james
 * @Date: 2023/9/6 14:42
 * @Description: 异步初始化
 */

class AsyncAppTaskFactory : Project.TaskFactory(AsyncAppTaskCreator())

fun startForTestLockableAnchorByDsl(listener: (lockableAnchor: LockableAnchor) -> Unit): LockableAnchor? {
    val manager = AnchorsManager.getInstance()
        .debuggable { true }
        .taskFactory { AsyncAppTaskFactory() }
        .block(Init.TASK_ID) {
            listener.invoke(it)
        }.graphics { arrayOf(Init.TASK_ID) }
        .startUp()
    return manager.getLockableAnchors()[Init.TASK_ID]
}


class AsyncAppTaskCreator : TaskCreator {
    override fun createTask(taskName: String): Task {
        return when (taskName) {
            Init.TASK_ID -> Init()
            else -> Init()
        }
    }
}

/**
 * 异步 初始化
 */
class Init : Task(TASK_ID, true) {
    companion object {
        const val TASK_ID = "1"
    }

    override fun run(name: String) {
        MMKV.initialize(appContext)
        // 初始化BugLy
        CrashReport.initCrashReport(
            appContext,
            if (BuildConfig.DEBUG) "d6d0803ef0" else "d6d0803ef0",
            BuildConfig.DEBUG
        )
    }
}





