package com.tools.glide

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tools.glide.GlideUtils.defErrorImg
import com.tools.glide.GlideUtils.defPlaceImg
import com.tools.glide.listener.OnGlideRequestListener

/**
 * Author: James
 * Created: 2025/03/26 15:14
 * Description:
 */
object GlideGIfUtils {

    /**
     * 加载Gif图片
     * @param imgUrl
     */
    @JvmStatic
    fun ImageView.loadGif(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = GlideUtils.defErrorImg,
        onRequestListener: OnGlideRequestListener? = null,
    ) {
        if (!GlideUtils.checkNull(this, imgUrl)) return
        loadGlideGif(
            context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).into(this)
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
    fun loadGlideGif(
        context: Context,
        imageUrl: Any?,
        overrideWidth: Int = 0,
        overrideHeight: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        memoryCache: Boolean = false,
        diskCache: DiskCacheStrategy = DiskCacheStrategy.RESOURCE,
        onRequestListener: OnGlideRequestListener? = null,
    ): RequestBuilder<GifDrawable> {
        val glide = Glide.with(context).asGif().load(imageUrl)
        glide.apply {
            override(overrideWidth, overrideHeight)
            placeholder(placeHolderImg)
            error(errorHolderImg)
            skipMemoryCache(memoryCache)
            diskCacheStrategy(diskCache)
            listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    onRequestListener?.onRequest(false)
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
        }
        return glide
    }
}