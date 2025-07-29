package com.frame.base.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.frame.base.ext.inflateBinding
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel


/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel + DateBinding BaseFragment
 */
abstract class BaseDBFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMFragment<VM>(),
    BaseIView {

    private var _binding: DB? = null

    val bind: DB get() = _binding!!

    /**
     * 创建 DataBinding
     */
    override fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = inflateBinding(inflater, container, false)
        _binding!!.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}