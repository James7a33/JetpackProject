package com.tools.picture

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.luck.picture.lib.entity.LocalMedia
import com.tools.logger.logA
import java.io.File

/**
 * Author: james
 * Created: 2024/8/26 14:33
 * Description: 针对华为无法获取图片地址
 */
fun LocalMedia.getImgPath(context: Context): String {
    var path = this.path

    if (isCut && cutPath?.isNotEmpty() == true) {
        path = cutPath
    }
    if (isOriginal && originalPath?.isNotEmpty() == true) {
        path = originalPath
    }
    if (isCompressed && compressPath?.isNotEmpty() == true) {
        path = compressPath
    }

    if (!path.isNullOrEmpty()) {
        return try {
            if (path.startsWith("content://")) {
                val uri = Uri.parse(path)
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                    val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    val imgPath = cursor.getString(index)
                    File(imgPath).absolutePath
                } ?: run {
                    "Cursor is null for uri: $uri".logA("Picture ImagePath")
                    path
                }
            } else {
                path
            }
        } catch (e: Exception) {
            "Error retrieving image path from URI: $path".logA("Picture ImagePath")
            ""
        }
    }
    return path ?: ""
}
