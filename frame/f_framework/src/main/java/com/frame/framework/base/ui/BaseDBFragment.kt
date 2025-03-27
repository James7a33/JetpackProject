package com.frame.framework.base.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.frame.framework.base.ui.BaseVMFragment
import com.frame.framework.base.view.BaseIView
import com.frame.framework.base.vm.BaseViewModel
import com.frame.framework.ext.inflateBinding

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+DateBinding BaseFragment
 */
abstract class BaseDBFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMFragment<VM>(),
    BaseIView {

    lateinit var bind: DB

    /**
     * 创建 DataBinding
     */
    override fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        bind = inflateBinding(inflater, container, false)
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }
}