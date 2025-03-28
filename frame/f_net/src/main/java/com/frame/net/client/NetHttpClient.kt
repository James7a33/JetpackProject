package com.frame.net.client

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RawRes
import com.frame.net.interceptor.LogInterceptor
import com.frame.net.interceptor.MyHeadInterceptor
import com.frame.net.interceptor.TokenOutInterceptor
import com.jeremyliao.liveeventbus.BuildConfig
import okhttp3.OkHttpClient
import rxhttp.wrapper.ssl.HttpsUtils
import java.net.Proxy
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.X509TrustManager


/**
 * @Author: james
 * @Date: 2024/3/18 17:21
 * @Description: 自定义 client
 */
@SuppressLint("StaticFieldLeak")
object NetHttpClient {

    private val myCrt: X509Certificate by lazy { getCrt(/*R.raw.my_ca*/ 0) }

    private lateinit var context: Context

    private fun getCrt(@RawRes raw: Int): X509Certificate {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val input = context.resources.openRawResource(raw)
        input.use {
            return certificateFactory.generateCertificate(input) as X509Certificate
        }
    }


    @SuppressLint("CustomX509TrustManager")
    private fun getTrustManagerInRelease(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String?) {
                val myCrt: X509Certificate = myCrt
                if (chain[0].subjectDN.name == myCrt.subjectDN.name) {
                    if (!myCrt.signature!!.contentEquals(chain[0].signature)) {
                        throw SSLHandshakeException("签名不符！")
                    }
                }
            }
        }
    }

    fun getDefaultOkHttpClient(): OkHttpClient.Builder {
        //在这里面可以写你想要的配置 太多了，我就简单的写了一点，具体可以看rxHttp的文档，有很多
        val sslParams = HttpsUtils.getSslSocketFactory()

        return OkHttpClient.Builder()
            //使用CookieStore对象磁盘缓存,自动管理cookie 自动登录验证
//            .cookieJar(CookieStore(File(appContext.externalCacheDir, "RxHttpCookie")))
            .connectTimeout(60, TimeUnit.SECONDS)//读取连接超时时间 15秒
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            //不使用代理，直接连接到目标服务器
            .proxy(if (BuildConfig.DEBUG) null else Proxy.NO_PROXY)
            //添加缓存拦截器 可传入缓存天数，不传默认7天
//            .addInterceptor(CacheInterceptor(CacheStrategy(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE,7)))
            .addInterceptor(TokenOutInterceptor())
            .addInterceptor(MyHeadInterceptor())//自定义头部参数拦截器
            .addInterceptor(LogInterceptor())//添加Log拦截器
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier { _, _ -> true } //忽略host验证
    }
}