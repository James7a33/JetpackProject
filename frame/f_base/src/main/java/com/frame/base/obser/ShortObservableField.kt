package com.frame.base.obser

import androidx.databinding.ObservableField

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: 自定义的 Short 类型 ObservableField 提供了默认值，避免取值的时候还要判空
 */
class ShortObservableField(value: Short = 0) : ObservableField<Short>(value) {

    override fun get(): Short {
        return super.get()!!
    }

}