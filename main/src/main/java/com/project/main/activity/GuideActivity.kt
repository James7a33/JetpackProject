package com.project.main.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.business.common.ext.isLogin
import com.frame.base.ext.closeActivity
import com.frame.base.ui.BaseVBActivity
import com.frame.common.constant.ARouterPath
import com.frame.common.constant.Constants
import com.frame.common.constant.mmAPP
import com.frame.common.ext.getStringArrayExt
import com.frame.common.ext.setOnclick
import com.frame.common.ext.toStartActivity
import com.gyf.immersionbar.ImmersionBar
import com.project.main.adapter.GuideAdapter
import com.project.main.databinding.ActivityGuideBinding
import com.project.main.vm.GuideVm
import com.tools.logger.logA
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2024/4/8 11:07
 * @Description:
 */
class GuideActivity : BaseVBActivity<GuideVm, ActivityGuideBinding>() {

    companion object {
        private const val ANIMATION_DURATION = 1300
    }

    private val guideAdapter by lazy { GuideAdapter() }

    override fun titleBar(): String = ""

    override fun isTitleBarShow(): Boolean = false

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .transparentBar()
            .init()
    }

    override fun initView(savedInstanceState: Bundle?) {
        initViewPager()
        initObserver()
        updateUI(0)
    }

    private fun initViewPager() {
        bind.viewpager.apply {
            setCanLoop(false)
//            setPageTransformer(
//                PageTransformerFactory.createPageTransformer(transforms[Random().nextInt(7)])
//            )
            setIndicatorMargin(0, 0, 0, resources.getDimensionPixelOffset(Rs.dimen.dp_80))
            setIndicatorSliderGap(resources.getDimension(Rs.dimen.dp_10).toInt())
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorSliderRadius(
                resources.getDimension(Rs.dimen.dp_3).toInt(),
                resources.getDimension(Rs.dimen.dp_4_5).toInt()
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    "滑动到第${position}页".logA(TAG)
                    updateUI(position)
                }
            })
            setIndicatorSliderColor(
                ContextCompat.getColor(this@GuideActivity, Rs.color.white),
                ContextCompat.getColor(this@GuideActivity, Rs.color.white_30)
            )
            setAdapter(guideAdapter)
        }
    }

    private fun updateUI(position: Int) {
        bind.tvDescribe.text = getStringArrayExt(Rs.array.guide_desc_array)[position]
        val translationAnim = ObjectAnimator.ofFloat(bind.tvDescribe, "translationX", -120f, 0f)
        translationAnim.apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = DecelerateInterpolator()
        }
        val alphaAnimator = ObjectAnimator.ofFloat(bind.tvDescribe, "alpha", 0f, 1f)
        alphaAnimator.apply {
            duration = ANIMATION_DURATION.toLong()
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator)
        animatorSet.start()

        if (position == bind.viewpager.data.size - 1 && bind.tvNext.visibility == View.GONE) {
            bind.tvNext.visibility = View.VISIBLE
            ObjectAnimator
                .ofFloat(bind.tvNext, "alpha", 0f, 1f)
                .setDuration(ANIMATION_DURATION.toLong())
                .start()
        } else {
            bind.tvNext.visibility = View.GONE
        }
    }

    private fun initObserver() {
        vm.guideValue.observe(this) {
            bind.viewpager.create(it)
        }
    }

    override fun onBindViewClick() {
        setOnclick(bind.tvNext) {
            mmAPP.encode(Constants.App.GUIDE, true)
            if(isLogin()){
                toStartActivity(MainActivity::class.java)
                closeActivity(this)
            }else{
                toStartActivity(ARouterPath.Login.LOGIN)
            }
        }
    }
}