package com.frame.base.ext

import androidx.lifecycle.ViewModelProvider
import com.frame.base.ui.BaseVMActivity
import com.frame.base.ui.BaseVMFragment
import com.frame.base.vm.BaseViewModel
import java.lang.reflect.ParameterizedType


/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: ViewBinding DataBinding 反射
 */

/**
 * 获取当前类绑定的泛型 ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}
@Suppress("UNCHECKED_CAST")
fun <DB> getDbClazz(obj: Any): DB {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as DB
}


/**
 * 创建viewModel
 */
fun <VM: BaseViewModel> BaseVMActivity<VM>.createViewModel(): VM {
    return ViewModelProvider(this)[getVmClazz(this)]
}

/**
 * 创建viewModel
 */
fun <VM: BaseViewModel> BaseVMFragment<VM>.createViewModel(): VM {
    return ViewModelProvider(this)[getVmClazz(this)]
}






