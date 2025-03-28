package com.tools.glide.svga

import com.bumptech.glide.Glide
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.glideplugin.SVGATarget
import com.opensource.svgaplayer.glideplugin.asSVGA
import com.tools.logger.logA


/**
 * Author: James
 * Created: 2025/02/11 10:57
 * Description:svga 播放动画
 */

const val TAG = "LoadSVGA"

/**
 * @param url 地址
 * @param svgTag tag
 */
fun SVGAImageView.loadSVGA(
    url: String,
    svgTag: String = "",
) {
    Glide.with(context)
        .asSVGA().load(url)
        .skipMemoryCache(false)
        .into(SVGATarget(this, SVGADynamicEntity()))
    callback = object : SVGACallback {
        override fun onFinished() {
            "svga onFinished $svgTag".logA(TAG)
        }

        override fun onPause() {
            "svga onPause $svgTag".logA(TAG)
        }

        override fun onRepeat() {
            "svga onRepeat $svgTag".logA(TAG)
        }

        override fun onStep(frame: Int, percentage: Double) {
            "svga onStep " + svgTag + "\nframe=" + "第" + frame + "针\n" + "总帧数=" + percentage.logA(TAG)
        }
    }


}
