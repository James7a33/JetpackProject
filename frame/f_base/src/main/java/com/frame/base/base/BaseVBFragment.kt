package com.frame.base.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.frame.base.ext.inflateBinding
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel+ViewBinding BaseFragment
 */
abstract class BaseVBFragment<VM : BaseViewModel, VB : ViewBinding> : BaseVMFragment<VM>(),
    BaseIView {

    private var _binding: VB? = null

    val bind: VB get() = _binding!!

    /**
     * 创建 ViewBinding
     */
    override fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = inflateBinding(inflater, container, false)
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}