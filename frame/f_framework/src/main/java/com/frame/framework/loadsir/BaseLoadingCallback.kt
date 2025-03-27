package com.frame.framework.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.frame.framework.R

/**
 * @author: james
 * @Description:
 * @Date: 2023/8/1 14:51
 */
class BaseLoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_base_loading
    }

    /**
     * 是否是 点击不可重试
     */
    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}