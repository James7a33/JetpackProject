package com.frame.common.ext

import com.tools.logger.logA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach


/**
 * @Author: james
 * @Date: 2023/9/6 16:05
 * @Description: 倒计时
 */

/**
 * 倒计时扩展函数   协程
 * @param total     Long  倒计时的秒数 比如 10秒 默认60秒
 * @param onTick    Long  倒计时一次回调函数
 * @param onFinish        倒计时结束回调函数
 * @param scope           作用域
 * @return
 */
fun countDown(
    total: Long = 60,
    onTick: (Long) -> Unit,
    onFinish: () -> Unit,
    scope: CoroutineScope = GlobalScope
): Job {
    return flow {
        for (i in total downTo 1) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
        .onCompletion { onFinish.invoke() }
        .onEach { onTick.invoke(it) }
        .flowOn(Dispatchers.Main)
        .launchIn(scope)
}


///*懒加载 倒计时 */
//val countdownTimerUtils: CountdownTimerUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//    CountdownTimerUtils()
//}
//
///**
// * 倒计时扩展函数 CountdownTimerUtil
// *
// * @param total     倒计     毫秒  默认60秒
// * @param interval  间隔时间  毫秒
// * @param isSecond  返回单位  true 秒 or false 毫秒
// * @param onTick    isSecond true 秒 or false 毫秒
// * @param onFinish  结束
// */
//fun countDown(
//    total: Long,
//    interval: Long = 1000,
//    isSecond: Boolean = true,
//    onTick: (Long) -> Unit,
//    onFinish: () -> Unit
//) {
//    "倒计时=$total".logA("CountdownTimerUtil")
//    countdownTimerUtils.setOnCountdownListener(object : CountdownTimerUtils.OnCountdownListener {
//        override fun onTick(millisUntilFinished: Long) {
//            "倒计时：$millisUntilFinished".logA("countDown")
//            if (isSecond) {
//                onTick.invoke(millisUntilFinished / interval)
//            } else {
//                onTick.invoke(millisUntilFinished)
//            }
//        }
//
//        override fun onFinish() {
//            onFinish.invoke()
//            countdownTimerUtils.cancelCountdown()
//        }
//    })
//    countdownTimerUtils.startCountdown(total, interval)
//}
