package com.frame.base.loadsir.base

import com.frame.base.R
import com.kingja.loadsir.callback.Callback

/**
 * @author: james
 * @Description:
 * @Date: 2023/8/1 14:51
 */
class BaseErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_base_error
    }

}