package com.frame.base.ui

import android.view.View
import androidx.viewbinding.ViewBinding
import com.frame.base.ext.inflateBinding
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+ViewBinding BaseActivity
 */
abstract class BaseVBActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVMActivity<VM>(),
    BaseIView {

    lateinit var bind: VB

    override fun initViewDataBind(): View? {
        //利用反射 根据泛型得到 ViewDataBinding
        bind = inflateBinding()
        return bind.root
    }
}