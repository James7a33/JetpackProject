package com.frame.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @Author: james
 * @Date: 2023/8/1 16:21
 * @Description:自定义的 Byte MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}