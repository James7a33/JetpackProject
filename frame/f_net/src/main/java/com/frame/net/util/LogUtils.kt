package com.frame.net.util

import android.text.TextUtils
import android.util.Log

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
class LogUtils private constructor() {
    companion object {
        private const val DEFAULT_TAG = "MvvmHelper"
        private var isLog = true
        fun isLog(): Boolean {
            return isLog
        }

        fun setLog(isLog: Boolean) {
            Companion.isLog = isLog
//            XLog.init(isLog)
        }

        fun debugInfo(tag: String?, msg: String?) {
            if (!isLog || TextUtils.isEmpty(msg)) {
                return
            }
            Log.d(tag, msg ?: "")
        }

        fun debugInfo(msg: String?) {
            debugInfo(DEFAULT_TAG, msg)
        }

        fun warnInfo(tag: String?, msg: String?) {
            if (!isLog || TextUtils.isEmpty(msg)) {
                return
            }
            Log.w(tag, msg ?: "")
        }

        fun warnInfo(msg: String?) {
            warnInfo(DEFAULT_TAG, msg)
        }

        fun debugLongInfo(msg: String) {
            debugLongInfo(DEFAULT_TAG, msg)
        }

        /**
         * 这里使用自己分节的方式来输出足够长度的 message
         *
         * @param tag 标签
         * @param msg 日志内容
         */
        private fun debugLongInfo(tag: String?, msg: String) {
            if (!isLog || TextUtils.isEmpty(msg)) {
                return
            }
            val message = msg.trim { it <= ' ' }
            var index = 0
            val maxLength = 3500
            var sub: String
            while (index < message.length) {
                sub = if (message.length <= index + maxLength) {
                    message.substring(index)
                } else {
                    message.substring(index, index + maxLength)
                }
                index += maxLength
                Log.d(tag, sub.trim { it <= ' ' })
            }
        }
    }

    init {
        throw IllegalStateException("you can't instantiate me!")
    }
}