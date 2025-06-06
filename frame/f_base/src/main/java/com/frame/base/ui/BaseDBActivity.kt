package com.frame.base.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.frame.base.vm.BaseViewModel
import com.frame.base.ext.inflateBinding
import com.gyf.immersionbar.ImmersionBar
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel + DateBinding BaseActivity
 */
abstract class BaseDBActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var bind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImmersionBar()
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

    /**
     * 创建Databind
     */
    override fun initViewDataBind(): View? {
        // 创建Databind
        bind = inflateBinding()
        // 设置生命周期所有者
        bind.lifecycleOwner = this
        // 返回根视图
        return bind.root
    }
}
