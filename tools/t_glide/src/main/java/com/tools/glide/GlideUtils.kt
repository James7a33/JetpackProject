package com.tools.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.tools.glide.listener.OnGlideRequestListener
import com.tools.glide.transformation.BorderTransformation
import com.tools.glide.transformation.CircleBorderTransformation
import com.tools.glide.transformation.GrayscaleTransformation
import com.tools.glide.utils.getAppScreenWidth

/**
 * @Author: james
 * @Date: 2024/4/16 14:29
 * @Description:
 */
object GlideUtils {

    const val TAG = "GlideUtils"
    var defPlaceImg = R.mipmap.ic_img_place_holder
    var defErrorImg = R.mipmap.ic_img_error_holder

    /**
     * 检测是否为null
     *
     * @param img 图片控件
     * @param imgUrl 图片地址
     */
    fun checkNull(img: ImageView?, imgUrl: Any?) {
        if (img == null) {
            throw NullPointerException("GlideUtils ImageView is null")
        }
        if (imgUrl == null) {
            throw NullPointerException("GlideUtils imgUrl is null")
        }
    }

    /**
     * 获取Glide基础配置
     *
     * @param context 上下文
     * @param imgUrl  图片地址
     * @return
     */
    @JvmStatic
    fun getGlide(context: Context, imgUrl: Any?): RequestBuilder<Drawable> {
        return loadGlide(context, imgUrl)
    }

    /**
     * 加载图片
     *
     * @param imgUrl 图片地址
     * @param placeHolderImg 占位图
     * @param errorHolderImg 加载失败图
     */
    @JvmStatic
    fun ImageView?.load(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
        checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).into(this)
    }

    /**
     * 加载图片
     *
     * @param imgUrl 图片地址
     * @param placeHolderImg 占位图
     * @param errorHolderImg 加载失败图
     */
    @JvmStatic
    fun ImageView?.loadOverride(
        imgUrl: Any?,
        overrideWidth: Int = 0,
        overrideHeight: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            overrideWidth = overrideWidth,
            overrideHeight = overrideHeight,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).into(this)
    }

    /**
     * 加载图片 关闭缓存
     * @param imgUrl 图片地址
     */
    @JvmStatic
    fun ImageView?.loadNoCache(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            memoryCache = true,
            diskCache = DiskCacheStrategy.NONE,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).into(this)
    }

    /**
     * 加载椭圆图片
     *
     * @param imgUrl  图片地址
     * @param radius  圆角
     */
    @JvmStatic
    fun ImageView?.loadRadius(
        imgUrl: Any?,
        radius: Int,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).transform(CenterCrop(), RoundedCorners(radius)).into(this)
    }

    /**
     * 加载圆
     *
     * @param imgUrl  图片地址
     */
    @JvmStatic
    fun ImageView?.loadCircle(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        val options = RequestOptions.circleCropTransform()
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).apply(options).into(this)
    }

    /**
     * 加载圆边框
     *
     * @param imgUrl    图片地址
     * @param width     宽度
     * @param color     颜色
     */
    @JvmStatic
    fun ImageView?.loadBorder(
        imgUrl: Any?,
        width: Float = 0f,
        color: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).transform(BorderTransformation(width, color)).into(this)
    }

    /**
     * 加载圆边框
     *
     * @param imgUrl    图片地址
     * @param width     宽度
     * @param color     颜色
     */
    @JvmStatic
    fun ImageView?.loadCircleBorder(
        imgUrl: Any?,
        width: Float = 0f,
        color: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).transform(
            CenterCrop(), CircleBorderTransformation(width, color)
        ).into(this)
    }

    /**
     * 加载模糊图片
     *
     * @param imgUrl  图片地址
     */
    @SuppressLint("CheckResult")
    @JvmStatic
    fun ImageView?.loadGray(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        val options = RequestOptions.bitmapTransform(GrayscaleTransformation())
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).apply(options).into(this)
    }

    /**
     * 加载图片
     * 自定义设置
     *
     * @param imgUrl  图片地址
     */
    @JvmStatic
    fun ImageView?.loadOptions(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        options: RequestOptions,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).apply(options).into(this)
    }

    /**
     * 加载图片
     * 自定义设置
     * @param transformation
     * @param imgUrl  图片地址
     */
    @JvmStatic
    fun ImageView?.loadTransForm(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        transformation: BitmapTransformation,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            onRequestListener = onRequestListener
        ).transform(transformation).into(this)
    }

    /**
     * 适配屏幕宽度，高度自适应
     * width  dp 设定宽度 -1 屏幕宽度
     * height dp 设定高度 -1 图片资源高度
     * width or height 不为默认值，设定宽高，不自适应
     * @param imgUrl 图片地址
     */
    @JvmStatic
    fun ImageView?.loadSelf(
        imgUrl: Any?,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        onRequestListener: OnGlideRequestListener? = null
    ) {
       checkNull(this, imgUrl)
        loadGlide(
            this!!.context,
            imgUrl,
            placeHolderImg = placeHolderImg,
            errorHolderImg = errorHolderImg,
            diskCache = DiskCacheStrategy.DATA,
            onRequestListener = onRequestListener
        ).into(object : CustomTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable, transition: Transition<in Drawable?>?
            ) {
                val width = resource.intrinsicWidth
                val height = resource.intrinsicHeight
                val params = layoutParams
                params.width = getAppScreenWidth(context)
                val tempHeight = height * (params.width.toFloat() / width)
                params.height = tempHeight.toInt()
                layoutParams = params
                setImageDrawable(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
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
    @JvmStatic
    fun loadGlide(
        context: Context,
        imageUrl: Any?,
        overrideWidth: Int = 0,
        overrideHeight: Int = 0,
        @DrawableRes placeHolderImg: Int = defPlaceImg,
        @DrawableRes errorHolderImg: Int = defErrorImg,
        memoryCache: Boolean = false,
        diskCache: DiskCacheStrategy = DiskCacheStrategy.RESOURCE,
        onRequestListener: OnGlideRequestListener? = null
    ): RequestBuilder<Drawable> {
        val glide = Glide.with(context).load(imageUrl)
        glide.apply {
            if (overrideWidth != 0 || overrideHeight != 0) {
                override(overrideWidth, overrideHeight)
            }
            placeholder(placeHolderImg)
            error(errorHolderImg)
            skipMemoryCache(memoryCache)
            diskCacheStrategy(diskCache)
            listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    onRequestListener?.onRequest(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
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