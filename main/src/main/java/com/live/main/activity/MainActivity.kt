package com.live.main.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.frame.base.ui.BaseVBActivity

import com.frame.common.constant.ARouterPath
import com.frame.common.constant.Constants
import com.frame.common.ext.getStringArrayExt
import com.frame.common.ext.getStringExt
import com.james.main.R
import com.james.main.databinding.ActivityMainBinding
import com.live.main.vm.MainVM
import com.tools.logger.logA
import com.tools.toast.ext.toastShort
import com.main.res.R as Rs


@Route(path = ARouterPath.Main.MAIN)
class MainActivity : BaseVBActivity<MainVM, ActivityMainBinding>() {

    //退出时间
    private var lastPressTime: Long = 0


    //icon
    private var mIconNorIds = intArrayOf(
        R.mipmap.ic_tab_home_normal,
        R.mipmap.ic_tab_dynamic_normal,
        R.mipmap.ic_tab_message_normal,
        R.mipmap.ic_tab_mine_normal,
    )

    private var mIconPreIds = intArrayOf(
        R.mipmap.ic_tab_home_press,
        R.mipmap.ic_tab_dynamic_press,
        R.mipmap.ic_tab_message_press,
        R.mipmap.ic_tab_mine_press,
    )

    override fun titleBar(): String = ""

    override fun isTitleBarShow(): Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        onBackPress()
    }

    private fun onBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val now = System.currentTimeMillis()
                if (lastPressTime == 0L || now - lastPressTime > Constants.App.APP_QUICK_TIME) {
                    getStringExt(Rs.string.again_logout).toastShort()
                    lastPressTime = now
                } else if (now - lastPressTime < Constants.App.APP_QUICK_TIME) {
                    moveTaskToBack(false)
                    // 触发系统默认的返回行为
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

}