package com.frame.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frame.base.R
import com.frame.base.databinding.LayoutTitleBarBinding
import com.frame.base.ext.closeActivity
import com.frame.base.ext.createViewModel
import com.frame.base.loading.LoadStatusEntity
import com.frame.base.loading.LoadingDialogEntity
import com.frame.base.loading.LoadingType
import com.frame.base.loadsir.base.BaseEmptyCallback
import com.frame.base.loadsir.base.BaseErrorCallback
import com.frame.base.loadsir.base.BaseLoadingCallback
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.noober.background.BackgroundLibrary
import com.tools.dialog.dismissLoadingExt
import com.tools.dialog.showLoadingExt
import com.tools.toast.ext.toastLong

/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel BaseActivity
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), BaseIView,
    OnTitleBarListener {

    val TAG: String = this.javaClass.simpleName

    //界面状态管理者
    private lateinit var uiStatusManger: LoadService<*>

    //当前Activity绑定的 ViewModel
    lateinit var vm: VM

    lateinit var titleBarBind: LayoutTitleBarBinding

    private var dataBindView: View? = null

    //自定义根布局
    lateinit var baseRootView: LinearLayout

    lateinit var baseContentView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BackgroundLibrary.inject(this)
        setContentView(R.layout.activity_base)
        //生成ViewModel
        vm = createViewModel()
        addLoadingUiChange(vm)
        //初始化 status View
        initUiStatusManger()
        //注册界面响应事件
        initView(savedInstanceState)
        //初始化请求成功方法
        onRequestSuccess()
        //初始化绑定点击方法
        onBindViewClick()
    }

    private fun initUiStatusManger() {
        baseRootView = findViewById(R.id.baseRootView)
        baseContentView = findViewById(R.id.baseContentView)
        //添加 titleBar
        if (isTitleBarShow()) {
            baseRootView.addView(
                if (getTitleBarView() == null) initTitleBar() else getTitleBarView()!!,
                0
            )
        }

        //添加 布局内容
        dataBindView = initViewDataBind()!!
        baseContentView.addView(dataBindView)

        uiStatusManger =
            if (getEmptyStateLayout() != null || getLoadingStateLayout() != null ||
                getErrorStateLayout() != null || getCustomStateLayout() != null
            ) {
                //如果子类有自定义CallBack ，那么就不能用 全局的，得新建一个 LoadSir
                val builder = LoadSir.beginBuilder()
                builder.addCallback(getEmptyStateLayout() ?: BaseEmptyCallback())
                builder.addCallback(getLoadingStateLayout() ?: BaseLoadingCallback())
                builder.addCallback(getErrorStateLayout() ?: BaseErrorCallback())
                getCustomStateLayout()?.forEach {
                    builder.addCallback(it)
                }
                builder.setDefaultCallback(SuccessCallback::class.java)
                builder.build()
                    .register(if (getLoadingView() == null) baseContentView else getLoadingView()!!) { onLoadRetry() }
            } else {
                //没有自定义CallBack 那么就用全局的LoadSir来注册
                LoadSir.getDefault()
                    .register(if (getLoadingView() == null) baseContentView else getLoadingView()!!) {
                        onLoadRetry()
                    }
            }
    }

    /**
     * 默认的 titleBar
     */
    private fun initTitleBar(): View {
        titleBarBind = LayoutTitleBarBinding.inflate(LayoutInflater.from(this))
        titleBarBind.titleBar.apply {
            title = titleBar()
            setOnTitleBarListener(this@BaseVMActivity)
        }
        return titleBarBind.root
    }

    /**
     * titleBar
     * 标题点击事件
     * @param titleBar
     */
    override fun onTitleClick(titleBar: TitleBar) {

    }

    /**
     * titleBar
     * 左标题点击事件
     * @param titleBar
     */
    override fun onLeftClick(titleBar: TitleBar) {
        closeActivity(this@BaseVMActivity)
    }

    /**
     * titleBar
     * 右标题点击事件
     * @param titleBar
     */
    override fun onRightClick(titleBar: TitleBar) {

    }

    /**
     * 设置titleBar
     */
    abstract fun titleBar(): String

    /**
     * 初始化view 这个方法会有延迟，因为使用了LoadSir，需要等待LoadSir注册完成后才能执行
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 点击事件方法 配合setOnclick()拓展函数调用，做到黄油刀类似的点击事件
     */
    open fun onBindViewClick() {}

    /**
     * 注册 UI 事件 监听请求时的回调UI的操作
     */
    private fun addLoadingUiChange(viewModel: BaseViewModel) {
        viewModel.loadingChange.run {
            loading.observe(this@BaseVMActivity) {
                when (it.loadingType) {
                    LoadingType.LOADING_DIALOG -> {
                        if (it.isShow) {
                            showLoading(it)
                        } else {
                            dismissLoading(it)
                        }
                    }

                    LoadingType.LOADING_CUSTOM -> {
                        if (it.isShow) {
                            showCustomLoading(it)
                        } else {
                            dismissCustomLoading(it)
                        }
                    }

                    LoadingType.LOADING_XML -> {
                        if (it.isShow) {
                            showLoadingUi(it.loadingMessage)
                        }
                    }

                    LoadingType.LOADING_NULL -> {
                    }
                }
            }
            showEmpty.observe(this@BaseVMActivity) {
                onRequestEmpty(it)
            }
            showError.observe(this@BaseVMActivity) {
                //如果请求错误 并且loading类型为 xml 那么控制界面显示为错误布局
                if (it.loadingType == LoadingType.LOADING_XML) {
                    showErrorUi(it.errorMessage)
                }
                onRequestError(it)
            }
            showSuccess.observe(this@BaseVMActivity) {
                //只有 当前 状态为 加载中时， 才切换成 成功页面
                if (getLoadingStateLayout() != null &&
                    uiStatusManger.currentCallback == getLoadingStateLayout()!!::class.java ||
                    uiStatusManger.currentCallback == BaseLoadingCallback::class.java
                ) {
                    showSuccessUi()
                }
            }
        }
    }

    /**
     * 请求列表数据为空时 回调
     * @param loadStatus LoadStatusEntity
     */
    override fun onRequestEmpty(loadStatus: LoadStatusEntity) {
        showEmptyUi()
    }

    /**
     * 请求接口失败回调，如果界面有请求接口，需要处理错误业务，请实现它 乳沟不实现那么 默认吐司错误消息
     * @param loadStatus LoadStatusEntity
     */
    override fun onRequestError(loadStatus: LoadStatusEntity) {
        loadStatus.errorMessage.toastLong()
    }

    /**
     * 请求成功的回调放在这里面 没干啥就是取了个名字，到时候好找
     */
    override fun onRequestSuccess() {}

    /**
     * 空界面，错误界面 点击重试时触发的方法，如果有使用 状态布局的话，一般子类都要实现
     */
    override fun onLoadRetry() {}

    /**
     * 显示 成功状态界面
     */
    override fun showSuccessUi() {
        uiStatusManger.showSuccess()
    }

    /**
     * 显示 错误 状态界面
     * @param message String
     */
    override fun showErrorUi(message: String) {
        uiStatusManger.showCallback(
            (getErrorStateLayout()?.javaClass ?: BaseErrorCallback::class.java).apply {
                uiStatusManger.setCallBack(this) { _, view ->
                    val messageView = view.findViewById<TextView>(R.id.state_error_tip)
                    messageView?.let {
                        it.text = message
                        it.visibility = if (message.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
            })
    }

    /**
     * 显示 空数据 状态界面
     */
    override fun showEmptyUi(message: String) {
        uiStatusManger.showCallback((getEmptyStateLayout()?.javaClass
            ?: BaseEmptyCallback::class.java).apply {
            uiStatusManger.setCallBack(this) { _, view ->
                val messageView = view.findViewById<TextView>(R.id.state_empty_tip)
                messageView?.let {
                    it.text = message
                    it.visibility = if (message.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }
        })
    }

    /**
     * 显示 loading 状态界面
     */
    override fun showLoadingUi(message: String) {
        uiStatusManger.showCallback(
            (getLoadingStateLayout()?.javaClass ?: BaseLoadingCallback::class.java).apply {
                uiStatusManger.setCallBack(this) { _, view ->
                    val messageView = view.findViewById<TextView>(R.id.state_loading_tip)
                    messageView?.let {
                        it.text = message
                        it.visibility = if (message.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
            })
    }

    /**
     * 显示自定义loading 在请求时 设置 loadingType类型为LOADING_CUSTOM 时才有效 可以根据setting中的requestCode判断
     * 具体是哪个请求显示该请求自定义的loading
     * @param setting LoadingDialogEntity
     */
    override fun showCustomLoading(setting: LoadingDialogEntity) {
        showLoadingExt(setting.loadingMessage)
    }

    /**
     * 隐藏自定义loading 在请求时 设置 loadingType类型为LOADING_CUSTOM 时才有效 可以根据setting中的requestCode判断
     * 具体是哪个请求隐藏该请求自定义的loading
     * @param setting LoadingDialogEntity
     */
    override fun dismissCustomLoading(setting: LoadingDialogEntity) {
        dismissLoadingExt()
    }

    override fun showLoading(setting: LoadingDialogEntity) {
        showLoadingExt(setting.loadingMessage)
    }

    override fun dismissLoading(setting: LoadingDialogEntity) {
        dismissLoadingExt()
    }

    override fun finish() {
        dismissLoadingExt()
        super.finish()
    }

    /**
     * 供子类BaseVmDbActivity 初始化 DataBinding ViewBinding操作
     */
    open fun initViewDataBind(): View? {
        return null
    }

    /**
     * 页面状态管理
     *
     * @return
     */
    override fun getEmptyStateLayout(): Callback? = null
    override fun getErrorStateLayout(): Callback? = null
    override fun getLoadingStateLayout(): Callback? = null
    override fun getCustomStateLayout(): List<Callback>? = null
}