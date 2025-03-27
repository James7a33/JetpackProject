package com.frame.framework.base.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.frame.framework.ext.inflateBinding
import com.frame.framework.base.vm.BaseViewModel
import com.frame.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+Datebind BaseActivity
 */
abstract class BaseDBActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var bind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImmersionBar()
    }

    /**
     * 创建Databind
     */
    override fun initViewDataBind(): View? {
        bind = inflateBinding()
        bind.lifecycleOwner = this
        return bind.root
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(Rs.color.white) //状态栏颜色，不写默认透明色
            .navigationBarColor(Rs.color.white) //导航栏颜色，不写默认透明色
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .fullScreen(false)
            .init()
    }
}