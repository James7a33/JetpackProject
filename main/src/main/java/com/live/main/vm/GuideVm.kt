package com.live.main.vm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.frame.base.appContext
import com.frame.base.ext.currentActivity
import com.frame.base.vm.BaseViewModel
import com.live.main.entity.GuideBean
import com.frame.common.ext.getStringArrayExt
import java.util.ArrayList
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2024/4/9 18:52
 * @Description:
 */
class GuideVm : BaseViewModel() {

    val guideValue = MutableLiveData<MutableList<GuideBean>>()

    private var mDrawableList: MutableList<Int> = ArrayList()

    init {
        initGuideData()
    }

    @SuppressLint("DiscouragedApi")
    private fun initGuideData() {
        for (i in 1..3) {
            currentActivity?.let {
                val drawable = it.resources.getIdentifier(
                    "ic_guide_$i",
                    "mipmap",
                    appContext.packageName
                )
                mDrawableList.add(drawable)
            }

        }
        val guideList = mutableListOf<GuideBean>()
        val homeTab = getStringArrayExt(Rs.array.guide_desc_array)
        mDrawableList.forEachIndexed { index, i ->
            guideList.add(GuideBean(i, homeTab[index]))
        }

        guideValue.value = guideList
    }


}