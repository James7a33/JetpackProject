package com.frame.framework.ext

import android.annotation.SuppressLint
import android.app.Activity
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.frame.res.R as Rs


/**
 * loading对话框
 */

@SuppressLint("StaticFieldLeak")
private var loadingPopup: LoadingPopupView? = null

/**
 * 打开等待框
 */
fun Activity.showLoadingExt(message: String = getString(Rs.string.loading)) {
    dismissLoadingExt()
    if (!this.isFinishing) {
        if (loadingPopup == null) {
            //弹出loading时 把当前界面的输入法关闭
            this.hideOffKeyboard()
            loadingPopup = XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .isLightNavigationBar(true)
                .asLoading(message, LoadingPopupView.Style.Spinner)
                .show() as LoadingPopupView
        }
        loadingPopup?.show()
    }
}

/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = getString(Rs.string.loading)) {
    dismissLoadingExt()
    activity?.let {
        if (!it.isFinishing) {
            if (loadingPopup == null) {
                //弹出loading时 把当前界面的输入法关闭
                it.hideOffKeyboard()
                loadingPopup = XPopup.Builder(requireActivity())
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .isLightNavigationBar(true)
                    .asLoading(message, LoadingPopupView.Style.Spinner)
                    .show() as LoadingPopupView
            }
            loadingPopup?.show()
        }
    }
}

/**
 * 关闭等待框
 */
fun Activity.dismissLoadingExt() {
    loadingPopup?.dismiss()
    loadingPopup = null
}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingPopup?.dismiss()
    loadingPopup = null
}