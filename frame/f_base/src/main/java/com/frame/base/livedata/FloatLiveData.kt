package com.frame.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:自定义的 Float 类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class FloatLiveData(value: Float = 0f) : MutableLiveData<Float>(value) {
    override fun getValue(): Float {
        return super.getValue()!!
    }
}