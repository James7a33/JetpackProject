package com.live.main.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.business.common.ext.isLogin
import com.frame.base.ui.BaseVBActivity
import com.frame.base.vm.BaseViewModel
import com.frame.base.ext.closeActivity
import com.frame.common.constant.ARouterPath
import com.frame.common.ext.countDown
import com.frame.common.ext.countdownTimerUtils
import com.frame.common.ext.getStringExt
import com.frame.common.ext.setOnclick
import com.frame.common.ext.toStartActivity
import com.gyf.immersionbar.ImmersionBar
import com.james.main.databinding.ActivitySplashBinding
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/26 08:51
 * @Description:
 */
@Route(path = ARouterPath.App.SPLASH)
class SplashActivity : BaseVBActivity<BaseViewModel, ActivitySplashBinding>() {

    private val countDownTimer: Long = 1000 * 4

    override fun titleBar(): String = ""

    override fun isTitleBarShow(): Boolean = false

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentBar()
            .fullScreen(true)
            .init()
    }

    override fun initView(savedInstanceState: Bundle?) {
//        无欢迎页
//        val isGuide = mmAPP.decodeBool(Constants.App.GUIDE)
//        if (isGuide) {
//            val randomNumber = Random.nextInt(1, 101)
//            if (randomNumber % 2 == 0) {
        initCountDown()
//            } else {
//                toMainActivity()
//            }
//        } else {
//            toStartActivity(GuideActivity::class.java)
//            closeActivity(this)
//        }
    }

    /**
     * 初始化倒计时
     */
    private fun initCountDown() {
        countDown(countDownTimer, isSecond = true, onTick = { seconds ->
            bind.tvSkip.text = getStringExt(Rs.string.splash_skip_second, seconds)
            if (seconds == 1L) {
                countdownTimerUtils.cancelCountdown()
                toMainActivity()
            }
        }, onFinish = {

        })
    }

    override fun onBindViewClick() {
        setOnclick(bind.tvSkip) {
            countdownTimerUtils.cancelCountdown()
            toMainActivity()
        }
    }

    /**
     * 跳转主页
     */
    private fun toMainActivity() {
        if (isLogin()) {
            toStartActivity(ARouterPath.Main.MAIN)
        } else {
            toStartActivity(ARouterPath.Login.LOGIN)
        }
        closeActivity(this)
    }
}