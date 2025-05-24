package com.frame.base.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import com.frame.base.vm.BaseViewModel
import com.frame.base.ext.inflateBinding

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel + DateBinding BaseActivity
 */
abstract class BaseDBActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var bind: DB

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