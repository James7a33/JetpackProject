package com.tools.permission

import com.hjq.permissions.Permission

/**
 * Author: James
 * Created: 2025/04/22 14:39
 * Description: 权限版本适配
 */
object Permission {
    val CAMERA_STORAGE by lazy {
        arrayOf(
            Permission.CAMERA,
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_AUDIO,
            Permission.READ_MEDIA_IMAGES,
            Permission.WRITE_EXTERNAL_STORAGE,
        )
    }

    val STORAGE by lazy {
        arrayOf(
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_AUDIO,
            Permission.READ_MEDIA_IMAGES,
            Permission.WRITE_EXTERNAL_STORAGE,
        )
    }
}