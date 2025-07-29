package com.frame.base.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.frame.base.R
import com.frame.base.ext.createViewModel
import com.frame.base.loading.LoadStatusEntity
import com.frame.base.loading.LoadingType
import com.frame.base.loadsir.base.BaseEmptyCallback
import com.frame.base.loadsir.base.BaseErrorCallback
import com.frame.base.loadsir.base.BaseLoadingCallback
import com.frame.base.view.BaseIView
import com.frame.base.vm.BaseViewModel
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tools.toast.ext.toastLong


/**
 * @Author: james
 * @Date: 2023/7/25 18:58
 * @Description: ViewModel BaseFragment
 */
abstract class BaseVMFragment<VM : BaseViewModel> : Fragment(), BaseIView {

    val TAG: String = this.javaClass.simpleName

    //界面状态管理者
    private lateinit var uiStatusManger: LoadService<*>

    //是否第一次加载
    private var isFirst: Boolean = true

    //当前Fragment绑定的泛型类ViewModel
    lateinit var vm: VM

    //父类activity
    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFirst = true
        val rootView = initViewDataBind(inflater, container)
        return if (getLoadingView() == null) {
            initUiStatusManger(rootView)
            container?.removeView(uiStatusManger.loadLayout)
            uiStatusManger.loadLayout
        } else {
            rootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = createViewModel()
        initStatusView()
        addLoadingUiChange(vm)
        //view加载完成后执行
        initView(savedInstanceState)
        onBindViewClick()
        initObserver()
        onRequestSuccess()
    }

    private fun initStatusView() {
        getLoadingView()?.let {
            //如果传入了自定义包裹view 将该view注册 做空错误 loading 布局处理
            initUiStatusManger(it)
        }
    }

    private fun initUiStatusManger(view: View?) {
        uiStatusManger =
            if (getEmptyStateLayout() != null || getLoadingStateLayout() != null || getErrorStateLayout() != null || getCustomStateLayout() != null) {
                //如果子类有自定义CallBack ，那么就不能用 全局的，得新建一个 LoadSir
                val builder = LoadSir.beginBuilder()
                builder.addCallback(getEmptyStateLayout() ?: BaseEmptyCallback())
                builder.addCallback(getLoadingStateLayout() ?: BaseLoadingCallback())
                builder.addCallback(getErrorStateLayout() ?: BaseErrorCallback())
                getCustomStateLayout()?.forEach {
                    builder.addCallback(it)
                }
                builder.setDefaultCallback(SuccessCallback::class.java)
                builder.build().register(view) {
                    onLoadRetry()
                }
            } else {
                //没有自定义CallBack 那么就用全局的LoadSir来注册
                LoadSir.getDefault().register(view) {
                    onLoadRetry()
                }
            }
    }


    /**
     * 初始化view操作
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    /**
     * 创建观察者
     */
    open fun initObserver() {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            view?.post {
                lazyLoadData()
                isFirst = false
            }
        }
    }

    /**
     * 子类可传入需要被包裹的View，做状态显示-空、错误、加载
     * 如果子类不覆盖该方法 那么会将整个当前Fragment界面都当做View包裹
     */
    override fun getLoadingView(): View? {
        return null
    }

    /**
     * 点击事件方法 配合setOnclick()拓展函数调用，做到黄油刀类似的点击事件
     */
    open fun onBindViewClick() {}

    /**
     * 注册 UI 事件 监听请求时的回调UI的操作
     */
    private fun addLoadingUiChange(viewModel: BaseViewModel) {
        viewModel.loadingChange.run {
            loading.observe(viewLifecycleOwner) {
                when (it.loadingType) {
                    //通用弹窗Dialog
                    LoadingType.LOADING_DIALOG -> {
                        if (it.isShow) {
                            showLoading(it)
                        } else {
                            dismissLoading(it)
                        }
                    }
                    //不同的请求自定义loading
                    LoadingType.LOADING_CUSTOM -> {
                        if (it.isShow) {
                            showCustomLoading(it)
                        } else {
                            dismissCustomLoading(it)
                        }
                    }
                    //请求时 xml显示 loading
                    LoadingType.LOADING_XML -> {
                        if (it.isShow) {
                            showLoadingUi(it.loadingMessage)
                        }
                    }

                    LoadingType.LOADING_NULL -> {
                    }
                }
            }
            //当分页列表数据第一页返回空数据时 显示空布局
            showEmpty.observe(viewLifecycleOwner) {
                onRequestEmpty(it)
            }
            //当请求失败时
            showError.observe(viewLifecycleOwner) {
                if (it.loadingType == LoadingType.LOADING_XML) {
                    showErrorUi(it.errorMessage)
                }
                onRequestError(it)
            }
            //如果是 LoadingType.LOADING_XML，当请求成功时 会显示正常的成功布局
            showSuccess.observe(viewLifecycleOwner) {
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
    override fun onRequestSuccess() {

    }

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
            }
        )
    }

    /**
     * 显示 空数据 状态界面
     */
    override fun showEmptyUi(message: String) {
        uiStatusManger.showCallback(
            (getEmptyStateLayout()?.javaClass
                ?: BaseEmptyCallback::class.java).apply {
                uiStatusManger.setCallBack(this) { _, view ->
                    val messageView = view.findViewById<TextView>(R.id.state_empty_tip)
                    messageView?.let {
                        it.text = message
                          it.visibility = if (message.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        )
    }

    /**
     * 显示 loading 状态界面
     */
    override fun showLoadingUi(message: String) {
        uiStatusManger.showCallback(
            (getLoadingStateLayout()?.javaClass
                ?: BaseLoadingCallback::class.java).apply {
                uiStatusManger.setCallBack(this) { _, view ->
                    val messageView = view.findViewById<TextView>(R.id.state_loading_tip)
                    messageView?.let {
                        it.text = message
                          it.visibility = if (message.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        )
    }

    /**
     * 供子类BaseVmDbActivity 初始化 DataBinding ViewBinding操作
     */
    open fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
    }

    override fun getEmptyStateLayout(): Callback? = null
    override fun getErrorStateLayout(): Callback? = null
    override fun getLoadingStateLayout(): Callback? = null
    override fun getCustomStateLayout(): List<Callback>? = null


}