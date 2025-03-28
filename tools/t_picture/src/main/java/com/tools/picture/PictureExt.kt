package com.tools.picture

import android.content.Context
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.tools.picture.engine.GlideEngine
import com.tools.picture.engine.ImageCompressFileEngine
import com.tools.picture.engine.ImageFileCropEngine
import com.tools.picture.engine.MeSandboxFileEngine
import com.tools.picture.lisener.OnPictureSelectorResultListener
import com.tools.picture.lisener.PictureResultCallbackListener
import com.tools.picture.thumb.VideoThumbListener

/**
 * Author: James
 * Created: 2025/02/24 10:12
 * Description:图片选择器
 */


/**
 * 媒体选择器
 * @param context 上下文
 * @param mediaType 媒体类型 SelectMimeType.ofAll() 0:全部 、SelectMimeType.ofImage() 1:拍照  、SelectMimeType.ofVideo() 2:录制视频 、 SelectMimeType.ofAudio() 3:录制音频
 * @param isCamera 是否显示拍照按钮 true 显示 false 不显示  默认 false
 * @param isFilterInvalidFile 是否筛选无效文件 true 筛选 false 不筛选 默认 true
 * @param isGif 是否显示gif图片 true 显示 false 不显示  默认 false
 * @param pictureMin 最小选择数量 默认 1
 * @param pictureMax 最大选择数量 默认 1
 * @param mediaMin 媒体（视频,音乐）最小选择数量 默认 1
 * @param mediaMax 媒体（视频,音乐）最大选择数量 默认 1
 * @param mediaSecondMin 媒体（视频,音乐）选择最小时间(秒) 默认 5
 * @param mediaSecondMax 媒体（视频,音乐）选择最大时间(秒) 默认 30
 * @param filterMediaMin 过滤媒体（视频,音乐）最小时间 默认 5
 * @param filterMediaMax 过滤媒体（视频,音乐）最大时间 默认 30
 * @param filterFileMax 文件最大选择大小 默认200m
 * @param recordMin 视频拍摄最小时间 默认 5
 * @param recordMax 视频拍摄最大时间 默认 30
 * @param withType 是否支持多种类型同时选择 mediaType= SelectMimeType.ofAll()
 * @param isCrop 是否裁剪 true 裁剪 false 不裁剪 默认 false
 * @param selectResult 已选择的结果
 * @param listener 选择结果回调
 */
fun pictureCreate(
    context: Context,
    mediaType: Int,
    isCamera: Boolean = false,
    isFilterInvalidFile: Boolean = true,
    isGif: Boolean = false,
    pictureMin: Int = 1,
    pictureMax: Int = 1,
    mediaMin: Int = 1,
    mediaMax: Int = 1,
    mediaSecondMin: Int = 5,
    mediaSecondMax: Int = 30,
    recordMin: Int = 5,
    recordMax: Int = 30,
    filterMediaMin: Int = 5,
    filterMediaMax: Int = 30,
    filterFileMax: Long = (200 * 1024 * 1024).toLong(),
    withType: Boolean = false,
    isCrop: Boolean = false,
    selectResult: ArrayList<LocalMedia> = ArrayList(),
    listener: OnPictureSelectorResultListener
) {
    PictureSelector.create(context)
        .openGallery(mediaType)
        .isPageStrategy(true, isFilterInvalidFile)
        .isDisplayCamera(isCamera)
        .isGif(isGif)
        .setMinSelectNum(pictureMin)
        .setMaxSelectNum(pictureMax)
        .setMinVideoSelectNum(mediaMin)
        .setMaxVideoSelectNum(mediaMax)
        .setSelectMinDurationSecond(mediaSecondMin)
        .setSelectMaxDurationSecond(mediaSecondMax)
        .setFilterVideoMinSecond(filterMediaMin)
        .setFilterVideoMaxSecond(filterMediaMax)
        .setRecordVideoMinSecond(recordMin)
        .setRecordVideoMaxSecond(recordMax)
        .setFilterMaxFileSize(filterFileMax)
        .isWithSelectVideoImage(withType)
        .setSandboxFileEngine(MeSandboxFileEngine())
        .setCropEngine(if (isCrop) ImageFileCropEngine() else null)
        .setSelectedData(selectResult)
        .setVideoThumbnailListener(VideoThumbListener(context))
        .setCompressEngine(ImageCompressFileEngine())
        .setImageEngine(GlideEngine())
        .forResult(PictureResultCallbackListener(context, listener))
}

/**
 * 打开相机
 * @param context 上下文
 * @param openCamera SelectMimeType.ofAll() 0:全部 、SelectMimeType.ofImage() 1:拍照  、SelectMimeType.ofVideo() 2:录制视频 、 SelectMimeType.ofAudio() 3:录制音频
 * @param mediaSecondMin 媒体（视频,音乐）选择最小时间(秒)
 * @param mediaSecondMax 媒体（视频,音乐）选择最大时间(秒)
 * @param recordMin 视频拍摄最小时间
 * @param recordMax 视频拍摄最大时间
 * @param listener 选择结果回调
 */
fun openCamera(
    context: Context, openCamera: Int, isRotateImage: Boolean,
    mediaSecondMin: Int, mediaSecondMax: Int, recordMin: Int, recordMax: Int,
    listener: OnPictureSelectorResultListener
) {
    PictureSelector.create(context)
        .openCamera(openCamera)
        .isCameraAroundState(isRotateImage)
        .setRecordVideoMinSecond(mediaSecondMin)
        .setRecordVideoMaxSecond(mediaSecondMax)
        .setSelectMinDurationSecond(recordMin)
        .setSelectMaxDurationSecond(recordMax)
        .setVideoThumbnailListener(VideoThumbListener(context))
        .setCompressEngine(ImageCompressFileEngine())
        .forResult(PictureResultCallbackListener(context, listener))
}


/**
 * 查看大图或预览视频
 * @param context 上下文
 * @param isHidePreviewDownload true 支持下载 false 不支持下载
 * @param isVideoPauseResumePlay true 支持暂停播放 false 不支持
 * @param isAutoVideoPlay true 自动播放 false 不自动播放
 * @param isDisplayDelete  true 可见删除 false 不可见删除
 * @param position 当前位置
 * @param localMedia 本地媒体
 */
fun openBigMedia(
    context: Context,
    isHidePreviewDownload: Boolean = true,
    isVideoPauseResumePlay: Boolean = true,
    isAutoVideoPlay: Boolean = true,
    isDisplayDelete: Boolean = false,
    position: Int,
    localMedia: ArrayList<LocalMedia>
) {
    PictureSelector.create(context)
        .openPreview()
        .isHidePreviewDownload(isHidePreviewDownload)
        .setImageEngine(GlideEngine())
        .isVideoPauseResumePlay(isVideoPauseResumePlay)
        .isAutoVideoPlay(isAutoVideoPlay)
        .startActivityPreview(position, isDisplayDelete, localMedia)
}

/**
 * 查看大图或预览视频
 * @param context 上下文
 * @param isHidePreviewDownload true 支持下载 false 不支持下载
 * @param isDisplayDelete 是否可见删除 true 可见 false 不可见
 * @param imgUrl 图片地址
 */
fun openBigMedia(
    context: Context,
    isHidePreviewDownload: Boolean = true,
    isVideoPauseResumePlay: Boolean = true,
    isAutoVideoPlay: Boolean = true,
    isDisplayDelete: Boolean = false,
    imgUrl: String
) {
    val localMedia = java.util.ArrayList<LocalMedia>()
    for (url in imgUrl.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
        localMedia.add(LocalMedia.generateHttpAsLocalMedia(url))
    }
    openBigMedia(
        context,
        isHidePreviewDownload,
        isDisplayDelete,
        isVideoPauseResumePlay,
        isAutoVideoPlay,
        0,
        localMedia
    )
}


