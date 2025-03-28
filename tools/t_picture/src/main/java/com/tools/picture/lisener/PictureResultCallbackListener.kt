package com.tools.picture.lisener

import android.content.Context
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.MediaUtils
import com.luck.picture.lib.utils.PictureFileUtils
import com.tools.logger.logA
import java.util.ArrayList

/**
 * Author: James
 * Created: 2025/02/24 10:46
 * Description: 选择返回结果
 */
class PictureResultCallbackListener(
    private val context: Context,
    private val listener: OnPictureSelectorResultListener
) :
    OnResultCallbackListener<LocalMedia> {

    override fun onResult(result: ArrayList<LocalMedia>) {
        listener.onResult(result)
        result.forEach { media ->
            if (media.width == 0 || media.height == 0) {
                if (PictureMimeType.isHasImage(media.mimeType)) {
                    val imageExtraInfo = MediaUtils.getImageSize(context, media.path)
                    media.width = imageExtraInfo.width
                    media.height = imageExtraInfo.height
                } else if (PictureMimeType.isHasVideo(media.mimeType)) {
                    val videoExtraInfo = MediaUtils.getVideoSize(context, media.path)
                    media.width = videoExtraInfo.width
                    media.height = videoExtraInfo.height
                }
            }
            val sb = StringBuilder()
            sb.append("文件名: ").append(media.fileName).append("\n")
            sb.append("是否压缩: ").append(media.isCompressed).append("\n")
            sb.append("压缩路径: ").append(media.compressPath).append("\n")
            sb.append("初始路径: ").append(media.path).append("\n")
            sb.append("绝对路径: ").append(media.realPath).append("\n")
            sb.append("是否裁剪: ").append(media.isCut).append("\n")
            sb.append("裁剪路径: ").append(media.cutPath).append("\n")
            sb.append("是否开启原图: ").append(media.isOriginal).append("\n")
            sb.append("原图路径: ").append(media.originalPath).append("\n")
            sb.append("沙盒路径: ").append(media.sandboxPath).append("\n")
            sb.append("水印路径: ").append(media.watermarkPath).append("\n")
            sb.append("视频缩略图: ").append(media.videoThumbnailPath).append("\n")
            sb.append("原始宽高: ").append(media.width).append("x").append(media.height).append("\n")
            sb.append("裁剪宽高: ").append(media.cropImageWidth).append("x") .append(media.cropImageHeight).append("\n")
            sb.append("文件大小: ").append(PictureFileUtils.formatAccurateUnitFileSize(media.size)).append("\n")
            sb.append("文件时长: ").append(media.duration)
            sb.toString().logA("PictureUtils")
        }
    }

    override fun onCancel() {
    }
}