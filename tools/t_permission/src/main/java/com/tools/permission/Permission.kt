package com.tools.permission

import android.os.Build
import com.hjq.permissions.Permission

/**
 * Author: James
 * Created: 2025/04/22 14:39
 * Description: 权限版本适配
 */
object Permission {

    val CAMERA_STORAGE by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) arrayOf(
            Permission.CAMERA,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        ) else arrayOf(
            Permission.CAMERA,
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_AUDIO,
            Permission.READ_MEDIA_IMAGES,
        )
    }

    val STORAGE by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) arrayOf(
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        ) else arrayOf(
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_IMAGES,
        )
    }
}