package com.frame.framework.loadsir

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.frame.framework.R

/**
 * @author: james
 * @Description:
 * @Date: 2023/8/1 14:51
 */
class BaseEmptyCallback : Callback() {

    companion object {
        fun emptyView(context: Context): View {
            return LayoutInflater.from(context).inflate(R.layout.layout_base_empty, null)
        }
    }

    override fun onCreateView(): Int {
        return R.layout.layout_base_empty
    }

}