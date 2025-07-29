package com.frame.base.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.frame.base.ext.inflateBinding
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel
import com.gyf.immersionbar.ImmersionBar
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+ViewBinding BaseActivity
 */
abstract class BaseVBActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVMActivity<VM>(),
    BaseIView {

    lateinit var bind: VB

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

    override fun initViewDataBind(): View? {
        //利用反射 根据泛型得到 ViewDataBinding
        bind = inflateBinding()
        return bind.root
    }
}