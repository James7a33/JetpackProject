package com.frame.base.obser

import androidx.databinding.ObservableField

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description: 自定义的 Float 类型 ObservableField 提供了默认值，避免取值的时候还要判空
 */
class FloatObservableField(value: Float = 0f) : ObservableField<Float>(value) {

    override fun get(): Float {
        return super.get()!!
    }

}