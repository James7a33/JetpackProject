package com.frame.web.activity

import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.frame.common.constant.ARouterPath
import com.frame.web.base.BaseWebActivity
import com.frame.web.vm.BrowserVM
import com.tools.web.databinding.ActivityBrowserBinding

/**
 * @Author: james
 * @Date: 2023/8/3 17:19
 * @Description:浏览器
 */
@Route(path = ARouterPath.Web.BROWSER)
class BrowserActivity : BaseWebActivity<BrowserVM, ActivityBrowserBinding>() {

    override fun titleBar(): String = ""

    override fun getAgentWebParent(): ViewGroup = bind.content

    override fun initView(savedInstanceState: Bundle?) {

    }
}