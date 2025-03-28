package com.tools.picture.engine

import android.content.Context
import android.net.Uri
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.File
import java.util.ArrayList

/**
 * Author: James
 * Created: 2025/02/24 10:44
 * Description: 图片压缩引擎
 */
class ImageCompressFileEngine: CompressFileEngine {
    override fun onStartCompress(
        context: Context?,
        source: ArrayList<Uri>?,
        call: OnKeyValueResultCallbackListener?
    ) {
        Luban.with(context).load(source).ignoreBy(100)
            .setCompressListener(object : OnNewCompressListener {
                override fun onStart() {
                }

                override fun onSuccess(source: String, compressFile: File) {
                    call?.onCallback(source, compressFile.absolutePath)
                }

                override fun onError(source: String, e: Throwable) {
                    call?.onCallback(source, null)
                }
            }).launch()
    }
}