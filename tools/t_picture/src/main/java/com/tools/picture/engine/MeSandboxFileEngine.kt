package com.tools.picture.engine

import android.content.Context
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils

/**
 * Author: James
 * Created: 2025/02/24 10:36
 * Description:自定义沙盒文件处理
 */
class MeSandboxFileEngine : UriToFileTransformEngine {
    override fun onUriToFileAsyncTransform(
        context: Context,
        srcPath: String,
        mineType: String,
        call: OnKeyValueResultCallbackListener
    ) {
        call.onCallback(
            srcPath,
            SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
        )
    }
}