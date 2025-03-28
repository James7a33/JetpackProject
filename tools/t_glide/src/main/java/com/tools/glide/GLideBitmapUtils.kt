package com.tools.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.tools.glide.GlideUtils.checkNull
import com.tools.glide.GlideUtils.defErrorImg
import com.tools.glide.GlideUtils.defPlaceImg
import com.tools.glide.listener.OnGlideRequestListener

/**
 * Author: James
 * Created: 2025/03/26 15:11
 * Description:
 */

object GlideBitmapUtils {


    /**
     * 带资源回调
     * @param imgUrl
     * @param callBack 根据资源回调，做一些处理
     */
    @JvmStatic
    fun ImageView.loadBitmap(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null,
        callBack: (Bitmap) -> Unit
    ) {
        if (!checkNull(this, imgUrl)) return
        loadGlideBitmap(
            context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                setImageBitmap(bitmap)
                callBack(bitmap)
            }
        })
    }


    /**
     * Glide 配置
     *
     * @param context           上下文
     * @param imageUrl          图片地址
     * @param overrideWidth     缩略图 宽度
     * @param overrideHeight    缩略图 高度
     * @param placeHolderImg    占位符
     * @param errorHolderImg    加载错误图片
     * @param memoryCache       内存缓存 false 启动 true 关闭
     * @param diskCache         磁盘缓存 DiskCacheStrategy.RESOURCE
     * @return
     */
    @SuppressLint("CheckResult")
    fun loadGlideBitmap(
        context: Context,
        imageUrl: Any?,
        overrideWidth: Int = 0,
        overrideHeight: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        memoryCache: Boolean = false,
        diskCache: DiskCacheStrategy = DiskCacheStrategy.RESOURCE,
        onRequestListener: OnGlideRequestListener? = null
    ): RequestBuilder<Bitmap> {
        val glide = Glide.with(context).asBitmap().load(imageUrl)
        glide.apply {
            override(overrideWidth, overrideHeight)
            placeholder(placeHolderImg)
            error(errorHolderImg)
            skipMemoryCache(memoryCache)
            diskCacheStrategy(diskCache)
            listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    onRequestListener?.onRequest(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    onRequestListener?.onRequest(true)
                    return false
                }
            })
        }
        return glide
    }
}
