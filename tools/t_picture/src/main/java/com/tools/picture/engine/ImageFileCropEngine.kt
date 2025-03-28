package com.tools.picture.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.engine.CropFileEngine
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine

/**
 * Author: James
 * Created: 2025/02/24 10:38
 * Description: 图片自定义裁剪
 */
class ImageFileCropEngine: CropFileEngine {

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return UCrop
     */
    private fun buildOptions(): UCrop.Options {
        val options = UCrop.Options()
        options.setHideBottomControls(true)
        options.setFreeStyleCropEnabled(true)
        options.setShowCropFrame(true)
        options.setShowCropGrid(false)
        options.setCircleDimmedLayer(false)
        options.withAspectRatio(1f, 1f)
        options.isCropDragSmoothToCenter(false)
        options.setMaxScaleMultiplier(100f)
        return options
    }

    override fun onStartCrop(
        fragment: Fragment?,
        srcUri: Uri?,
        destinationUri: Uri?,
        dataSource: ArrayList<String>?,
        requestCode: Int
    ) {
        val options: UCrop.Options = buildOptions()
        val uCrop = UCrop.of(srcUri!!, destinationUri!!, dataSource)
        uCrop.withOptions(options)
        uCrop.setImageEngine(object : UCropImageEngine {
            override fun loadImage(context: Context, url: String, imageView: ImageView) {
                Glide.with(context).load(url).override(180, 180).into(imageView)
            }

            override fun loadImage(
                context: Context,
                url: Uri,
                maxWidth: Int,
                maxHeight: Int,
                call: UCropImageEngine.OnCallbackListener<Bitmap>
            ) {
                Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .override(maxWidth, maxHeight)
                    .into(object : CustomTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {
                            call.onCall(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            call.onCall(null)
                        }
                    })
            }
        })
        uCrop.start(fragment!!.requireActivity(), fragment!!, requestCode)
    }

}

