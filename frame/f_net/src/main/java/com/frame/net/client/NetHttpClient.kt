package com.frame.net.client

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RawRes
import com.frame.base.appContext
import com.frame.net.BuildConfig
import com.frame.net.NetConstants
import com.frame.net.interceptor.LogInterceptor
import okhttp3.Interceptor
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

    private fun getCrt(@RawRes raw: Int): X509Certificate {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val input = appContext.resources.openRawResource(raw)
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

    /**
     * 初始化 OkHttpClient
     * @param context 上下文
     * @param interceptors 自定义拦截器集合
     */
    fun getDefaultOkHttpClient(
        interceptors: MutableList<Interceptor>
    ): OkHttpClient.Builder {
        //在这里面可以写你想要的配置 太多了，我就简单的写了一点，具体可以看rxHttp的文档，有很多
        val sslParams = HttpsUtils.getSslSocketFactory()
        val okHttpClient = OkHttpClient.Builder()
        //使用CookieStore对象磁盘缓存,自动管理cookie 自动登录验证
        //okHttpClient.cookieJar(CookieStore(File(appContext.externalCacheDir, "RxHttpCookie")))
        okHttpClient.connectTimeout(NetConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)//读取连接超时时间 15秒
        okHttpClient.readTimeout(NetConstants.READ_TIME_OUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(NetConstants.WRITE_TIME_OUT, TimeUnit.SECONDS)
        //不使用代理，直接连接到目标服务器
        okHttpClient.proxy(if (BuildConfig.DEBUG) null else Proxy.NO_PROXY)
        //添加缓存拦截器 可传入缓存天数，不传默认7天
        //.addInterceptor(CacheInterceptor(CacheStrategy(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE,7)))
        interceptors.forEach {
            okHttpClient.addInterceptor(it)
        }
        okHttpClient.addInterceptor(LogInterceptor())//添加Log拦截器
        okHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
        okHttpClient.hostnameVerifier { _, _ -> true } //忽略host验证
        return okHttpClient

    }
}