package com.tools.web.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.frame.base.databinding.LayoutTitleBarBinding
import com.frame.base.ext.closeActivity
import com.frame.base.vm.BaseViewModel
import com.frame.base.ext.inflateBinding
import com.frame.base.ext.getVmClazz
import com.frame.common.ext.getColorExt
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebSettingsImpl
import com.just.agentweb.AgentWebUIControllerImplBase
import com.just.agentweb.DefaultWebClient.OpenOtherPageWays
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.IWebLayout
import com.just.agentweb.MiddlewareWebChromeBase
import com.just.agentweb.MiddlewareWebClientBase
import com.just.agentweb.PermissionInterceptor
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient


import com.tools.web.databinding.ActivityBaseWebBinding
import com.main.res.R as Rs

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: 浏览器 ViewModel+DateBinding BaseActivity
 */
abstract class BaseWebActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    //根布局
    private val baseBind by lazy { ActivityBaseWebBinding.inflate(LayoutInflater.from(this)) }

    //内容布局
    lateinit var bind: DB

    //viewModel
    lateinit var vm: VM

    //浏览器
    protected lateinit var mAgentWeb: AgentWeb

    //Ui 更改
    private lateinit var mAgentWebUIController: AgentWebUIControllerImplBase

    //chrome 设置
    private lateinit var mMiddleWareWebChrome: MiddlewareWebChromeBase

    //webClient 设置
    private lateinit var mMiddleWareWebClient: MiddlewareWebClientBase

    //error View
    private var mErrorView: View? = null

    private lateinit var webUrl: String

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(Rs.color.white) //状态栏颜色，不写默认透明色
            .navigationBarColor(Rs.color.white) //导航栏颜色，不写默认透明色
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .fullScreen(false)
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseBind.root)
        intent?.let {
            webUrl = it.getStringExtra("url") ?: "Https://www.baidu.com"
        }
        //生成ViewModel
        vm = ViewModelProvider(this)[getVmClazz(this)]
        //初始化 status View
        initUiStatusManger()
        //注册界面响应事件
        initView(savedInstanceState)
        initImmersionBar()
        buildAgentWeb()
    }

    //初始化 status View
    private fun initUiStatusManger() {
        bind = inflateBinding()
        bind.lifecycleOwner = this
        if (isTitleBarShow()) {
            baseBind.root.addView(
                if (getTitleBarView() == null) initTitleBar() else getTitleBarView()!!,
                0
            )
        }
        baseBind.baseContentView.addView(bind.root)
    }

    /**
     * 初始化view 这个方法会有延迟，因为使用了LoadSir，需要等待LoadSir注册完成后才能执行
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 是否显示 titleBar
     */
    protected open fun isTitleBarShow(): Boolean {
        return true
    }

    /**
     * 设置titleBar
     */
    abstract fun titleBar(): String

    /**
     * 设置 titleBar的监听
     */
    private fun initTitleBar(): TitleBar {
        val titleBarView = LayoutTitleBarBinding.inflate(LayoutInflater.from(this))
        titleBarView.titleBar.apply {
            title = titleBar()
            setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(titleBar: TitleBar?) {
                    super.onLeftClick(titleBar)
                    closeActivity(this@BaseWebActivity)
                }

                override fun onRightClick(titleBar: TitleBar?) {
                    super.onRightClick(titleBar)
                }
            })
        }
        return titleBarView.titleBar
    }

    /**
     * 自定义 titleBar
     */
    protected open fun getTitleBarView(): View? {
        return null
    }

    protected open fun buildAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(getAgentWebParent(), ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
            .setWebChromeClient(getWebChromeClient())
            .setWebViewClient(getWebViewClient())
            .setWebView(getWebView())
            .setPermissionInterceptor(getPermissionInterceptor())
            .setWebLayout(getWebLayout())
            .setAgentWebUIController(getAgentWebUIController())
            .interceptUnkownUrl()
            .setOpenOtherPageWays(getOpenOtherAppWay())
            .useMiddlewareWebChrome(getMiddleWareWebChrome())
            .useMiddlewareWebClient(getMiddleWareWebClient())
            .setAgentWebWebSettings(getAgentWebSettings())
            .setMainFrameErrorView(getErrorLayoutEntity())
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .createAgentWeb()
            .ready()
            .go(getUrl())

        //调用 js
//        mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroidParam", "Hello ! Agentweb")
//
//        //js 调用 Android
//        mAgentWeb.jsInterfaceHolder.addJavaObject(
//            "android",
//            AndroidInterface(mAgentWeb, this.getActivity())
//        )

    }

    private fun getErrorLayoutEntity(): View =
        mErrorView ?: LayoutInflater.from(this).inflate(com.frame.base.R.layout.layout_base_empty, null)

    protected open fun setErrorLayoutEntity(view: View) {
        this.mErrorView = view
    }

    protected open fun getAgentWeb(): AgentWeb? {
        return mAgentWeb
    }

    protected open fun getUrl(): String {
        return webUrl
    }

    open fun getAgentWebSettings(): IAgentWebSettings<*>? {
        return AgentWebSettingsImpl.getInstance()
    }

    protected abstract fun getAgentWebParent(): ViewGroup

    @ColorInt
    protected open fun getIndicatorColor(): Int {
        return getColorExt(Rs.color.black)
    }

    protected open fun getIndicatorHeight(): Int {
        return 3
    }

    protected open fun getWebViewClient(): WebViewClient? {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    protected open fun getWebChromeClient(): WebChromeClient? {
        return object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                setTitle(title)
            }
        }
    }

    protected open fun getWebView(): WebView? {
        return null
    }

    protected open fun getWebLayout(): IWebLayout<*, *>? {
        return null
    }

    protected open fun getPermissionInterceptor(): PermissionInterceptor? {
        return null
    }

    open fun getAgentWebUIController(): AgentWebUIControllerImplBase? {
        return null
    }

    open fun getOpenOtherAppWay(): OpenOtherPageWays? {
        return null
    }

    protected open fun getMiddleWareWebClient(): MiddlewareWebClientBase {
        return object : MiddlewareWebClientBase() {}.also { mMiddleWareWebClient = it }
    }

    protected open fun getMiddleWareWebChrome(): MiddlewareWebChromeBase {
        return object : MiddlewareWebChromeBase() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                setTitle(title)
            }
        }.also { mMiddleWareWebChrome = it }
    }

    protected open fun setTitle(title: String) {
        if (isTitleBarShow()) {
            if (getTitleBarView() == null) {
                initTitleBar().title = if (title.length > 10) title.substring(0, 10) else title
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}