package com.frame.common.utils

import android.os.CountDownTimer

/**
 * @Author: zzw
 * @Date: 2024/4/8 14:26
 * @Description: 倒计时工具类
 */
class CountdownTimerUtils {

    private var countDownTimer: CountDownTimer? = null
    private var listener: OnCountdownListener? = null

    /**
     * 开始倒计时
     *
     * @param millisInFuture 总倒计时时间，单位毫秒
     * @param countDownInterval 倒计时间隔，单位毫秒
     */
    fun startCountdown(millisInFuture: Long, countDownInterval: Long) {
        countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                listener?.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                listener?.onFinish()
            }
        }
        countDownTimer?.start()
    }

    /**
     * 取消倒计时
     */
    fun cancelCountdown() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    /**
     * 设置倒计时监听器
     *
     * @param listener 倒计时监听器
     */
    fun setOnCountdownListener(listener: OnCountdownListener?) {
        this.listener = listener
    }

    /**
     * 倒计时监听器接口
     */
    interface OnCountdownListener {
        /**
         * 每隔一段时间触发的回调
         *
         * @param millisUntilFinished 剩余时间，单位毫秒
         */
        fun onTick(millisUntilFinished: Long)

        /**
         * 倒计时结束时触发的回调
         */
        fun onFinish()
    }
}
