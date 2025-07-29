package com.business.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager

/**
 * @author: james
 * @Description: 自定义viewpager
 * @Date: 2022/9/23 21:42
 */
class AutoHeightViewPager : ViewPager {
    private var mHeight = 0

    /** 已经获取到的高度下标 ： 当前的高度  */
    private var mCurPosition = 0

    /** 当前显示下标  */
    private var mPosition = 0

    /** 按下标存储View历史高度  */
    private val mChildrenViews: HashMap<Int, Int?> = LinkedHashMap()

    /** 记录页面是否存储了高度  */
    private val indexList: HashMap<Int, Boolean> = LinkedHashMap()

    /** 做自适应高度，必须先进行初始化标记  */
    fun initIndexList(size: Int) {
        mHeight = 0
        mCurPosition = 0
        mPosition = 0
        for (i in 0 until size) {
            /** 初始化高度存储状态  */
            indexList[i] = false
        }
    }

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        if (indexList.size > 0) {
            if (indexList[mPosition]!!) {
                height = mChildrenViews[mPosition]!!
            } else {
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    child.measure(
                        widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    )
                    val h = child.measuredHeight
                    if (h > height) {
                        height = h
                    }
                }
                mHeight = height
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }

    /**
     * 在viewpager 切换的时候进行更新高度
     */
    fun updateHeight(current: Int) {
        mPosition = current
        if (indexList.size > 0) {
            saveIndexData()
            if (indexList[current]!!) {
                var height = 0
                if (mChildrenViews[current] != null) {
                    height = mChildrenViews[current]!!
                }
                var layoutParams = layoutParams as LinearLayout.LayoutParams
                if (layoutParams == null) {
                    layoutParams =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
                } else {
                    layoutParams.height = height
                }
                setLayoutParams(layoutParams)
            }
            mCurPosition = current
        }
    }

    /**
     * 保存已经测绘好的高度
     */
    private fun saveIndexData() {
        if (!indexList[mCurPosition]!!) {
            /** 没保存高度时，保存  */
            indexList[mCurPosition] = true
            mChildrenViews[mCurPosition] = mHeight
        }
    }
}