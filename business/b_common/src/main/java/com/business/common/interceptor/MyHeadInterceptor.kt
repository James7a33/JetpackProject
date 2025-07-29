package com.business.common.interceptor


import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import com.frame.base.appContext
import com.tools.logger.logD
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale


/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description: 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    private val TAG = "MyHeadInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//            .addHeader("Authorization", SPUtils.getInstance().getString("user_token"))
            .addHeader("signature", "")
            .addHeader("oaid", "")
            .addHeader("currentTime", "")
            .addHeader("deviceId", "")
            .addHeader("onlyTag", getOnlyId())
            .addHeader("osVersion", "")
            .addHeader("phoneModel", "")
            .addHeader("osType", "")
            .addHeader("channelId", "")
            .addHeader("osName", "")
            .addHeader("appVersion", "")

        return chain.proceed(builder.build())
    }

    @SuppressLint("HardwareIds")
    private fun getOnlyId(): String {
        var mszUniqueID = ""
        try {
            val mszDevIDShort =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10 //13 digits

            val sb = StringBuilder()
                .append("Build.BOARD--->" + Build.BOARD)
                .append("\n")
                .append("Build.BRAND--->" + Build.BRAND)
                .append("\n")
                .append("Build.CPU_ABI--->" + Build.CPU_ABI)
                .append("\n")
                .append("Build.DEVICE--->" + Build.DEVICE)
                .append("\n")
                .append("Build.HOST--->" + Build.HOST)
                .append("\n")
                .append("Build.ID--->" + Build.ID)
                .append("\n")
                .append("Build.MANUFACTURER--->" + Build.MANUFACTURER)
                .append("\n")
                .append("Build.MODEL--->" + Build.MODEL)
                .append("\n")
                .append("Build.PRODUCT--->" + Build.PRODUCT)
                .append("\n")
                .append("Build.TAGS--->" + Build.TAGS)
                .append("\n")
                .append("Build.PRODUCT--->" + Build.PRODUCT)
                .append("\n")
                .append("Build.TYPE--->" + Build.TYPE)
                .append("\n")
                .append("Build.USER--->" + Build.USER)
            sb.toString().logD(TAG)

            var serial: String = ""

            try {
                Build::class.java.getField("SERIAL").let {
                    serial = it.get(null)?.toString() ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val mszAndroidID =
                Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)

            val mszLongID = serial + mszDevIDShort + mszAndroidID

            //md5加密生成唯一uuid
            var m: MessageDigest? = null
            try {
                m = MessageDigest.getInstance("MD5")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            m!!.update(mszLongID.toByteArray(), 0, mszLongID.length)

            val p_md5Data = m!!.digest()

            for (i in p_md5Data.indices) {
                val b = (0xFF and p_md5Data[i].toInt())
                if (b <= 0xF) mszUniqueID += "0"
                mszUniqueID += Integer.toHexString(b)
            }
            mszUniqueID = mszUniqueID.uppercase(Locale.getDefault())
            "最终组合uuid： $mszUniqueID".logD(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            "最终组合uuid： $mszUniqueID".logD(TAG)
        }
        return mszUniqueID
    }
}