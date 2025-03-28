package com.tools.glide.svga

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.AppGlideModule
import java.io.File

/**
 * Author: James
 * Created: 2025/02/11 10:41
 * Description: svg
 */
@GlideModule
class CustomGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val cacheSizeBytes = 200 * 1024 * 1024
        val cacheDir = File(context.cacheDir, "glide-svga")
        builder.setDiskCache(DiskLruCacheFactory(cacheDir.absolutePath, cacheSizeBytes.toLong()))
    }
}