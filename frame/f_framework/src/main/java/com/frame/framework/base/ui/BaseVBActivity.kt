package com.frame.framework.base.ui

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.frame.framework.base.view.BaseIView
import com.frame.framework.base.vm.BaseViewModel
import com.frame.framework.ext.inflateBinding
import com.gyf.immersionbar.ImmersionBar
import com.noober.background.BackgroundLibrary
import com.frame.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+ViewBinding BaseActivity
 */
abstract class BaseVBActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVMActivity<VM>(),
    BaseIView{

    lateinit var bind: VB

    override fun initViewDataBind(): View? {
        //利用反射 根据泛型得到 ViewDataBinding
        bind = inflateBinding()
        BackgroundLibrary.inject(this)
        return bind.root
    }

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
}