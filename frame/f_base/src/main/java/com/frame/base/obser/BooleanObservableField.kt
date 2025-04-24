package com.frame.base.obser

import androidx.databinding.ObservableField

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: 自定义的 Boolean 类型 ObservableField 提供了默认值，避免取值的时候还要判空
 */
class BooleanObservableField(value: Boolean = false) : ObservableField<Boolean>(value) {
    override fun get(): Boolean {
        return super.get()!!
    }

}