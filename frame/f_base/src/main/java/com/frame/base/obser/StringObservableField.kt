package com.frame.base.obser

import androidx.databinding.ObservableField

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: 自定义的 String 类型 ObservableField 提供了默认值，避免取值的时候还要判空
 */
open class StringObservableField(value: String = "") : ObservableField<String>(value) {

    override fun get(): String {
        return if(super.get().isNullOrEmpty()) "" else super.get()!!
    }

}