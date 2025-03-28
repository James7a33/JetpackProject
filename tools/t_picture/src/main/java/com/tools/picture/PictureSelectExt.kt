package com.tools.picture

import android.content.Context
import android.widget.ImageView
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.tools.glide.GlideUtils.load
import com.tools.picture.lisener.OnPictureSelectorResultListener

/**
 * Author: James
 * Created: 2025/02/24 13:03
 * Description: 选择媒体
 */

/**
 * 扩展ImageView的函数，用于选择头像
 *
 * 该函数通过调用 pictureCreate 方法来启动图片选择器，用户可以选择并裁剪图像作为头像
 * 选择完成后，通过回调函数返回用户选择的图片列表
 *
 * @param isCrop 是否裁剪 true 裁剪 false 不裁剪
 * @param callback 当前用选择图片地址
 */
fun ImageView.selectAvatar(
    isCrop: Boolean,
    callback: (imgUrl: String) -> Unit
) {
    pictureCreate(context, mediaType = SelectMimeType.ofImage(), isCrop = isCrop,
        listener = object : OnPictureSelectorResultListener {
            override fun onResult(localMedias: ArrayList<LocalMedia>) {
                val localMedia = localMedias[0]
                load(localMedia.getImgPath(context))
                callback.invoke(localMedia.getImgPath(context))
            }
        }
    )
}

/**
 * 选择单张图片
 * @param selectResult 用户已选择的图片列表，用于回显已选中的图片
 * @param callback 返回当前图片的地址
 */
fun ImageView.selectImgSinge(
    selectResult: ArrayList<LocalMedia>,
    callback: (imgUrl: String) -> Unit
) {
    pictureCreate(context, SelectMimeType.ofImage(), selectResult = selectResult,
        listener = object : OnPictureSelectorResultListener {
            override fun onResult(localMedias: ArrayList<LocalMedia>) {
                val localMedia = localMedias[0]
                load(localMedia.getImgPath(context))
                callback.invoke(localMedia.getImgPath(context))
            }
        }
    )
}

/**
 * 选择多个图片
 * @param selectResult 用户已选择的图片列表，用于回显已选中的图片
 * @param callback 返回当前图片的地址
 */
fun ImageView.selectImgMultiple(
    pictureMax: Int,
    selectResult: ArrayList<LocalMedia>,
    callback: (localMedias: ArrayList<LocalMedia>) -> Unit
) {
    pictureCreate(context, SelectMimeType.ofImage(),
        pictureMax = pictureMax, selectResult = selectResult,
        listener = object : OnPictureSelectorResultListener {
            override fun onResult(localMedias: ArrayList<LocalMedia>) {
                callback.invoke(localMedias)
            }
        }
    )
}

/**
 * 选择单个视频
 * @param mediaSecondMin 媒体（视频,音乐）选择最小时间(秒) 默认 5
 * @param mediaSecondMax 媒体（视频,音乐）选择最大时间(秒) 默认 30
 * @param filterMediaMin 过滤媒体（视频,音乐）最小时间 默认 5
 * @param filterMediaMax 过滤媒体（视频,音乐）最大时间 默认 30
 * @param filterFileMax 文件最大选择大小 默认200m
 * @param callback 返回当前视频的地址
 */
fun ImageView.selectVideoSinge(
    mediaSecondMin: Int, mediaSecondMax: Int, filterMediaMin: Int, filterMediaMax: Int,
    filterFileMax: Long, callback: (localMedia: LocalMedia) -> Unit
) {
    pictureCreate(context, SelectMimeType.ofVideo(),
        mediaSecondMin = mediaSecondMin, mediaSecondMax = mediaSecondMax,
        filterMediaMin = filterMediaMin, filterMediaMax = filterMediaMax,
        filterFileMax = filterFileMax,
        listener = object : OnPictureSelectorResultListener {
            override fun onResult(localMedias: ArrayList<LocalMedia>) {
                val localMedia = localMedias[0]
                load(localMedia.getImgPath(context))
                callback.invoke(localMedia)
            }
        }
    )
}

/**
 * 发布动态专属
 * @param callback 返回当前选择媒体数据
 */
fun selectMedia(
    context: Context,
    callback: (localMedia: ArrayList<LocalMedia>) -> Unit
) {
    pictureCreate(
        context, SelectMimeType.ofAll(),
        pictureMax = 9,
        mediaSecondMin = 3,
        mediaSecondMax = 15,
        filterMediaMin = 3,
        filterMediaMax = 15,
        recordMax = 15,
        recordMin = 3,
        listener = object : OnPictureSelectorResultListener {
            override fun onResult(localMedias: ArrayList<LocalMedia>) {
                callback.invoke(localMedias)
            }
        }
    )
}


