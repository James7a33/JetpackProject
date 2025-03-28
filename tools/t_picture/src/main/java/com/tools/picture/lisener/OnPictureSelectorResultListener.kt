package com.tools.picture.lisener

import com.luck.picture.lib.entity.LocalMedia

/**
 * Author: James
 * Created: 2025/02/24 10:13
 * Description: 选择图片返回结果
 */
interface OnPictureSelectorResultListener {

    fun onResult(localMedias: ArrayList<LocalMedia>)

}