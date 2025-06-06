package com.tools.dialog

import android.app.Activity
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback

/**
 * Author: james
 * Created: 2024/8/19 16:23
 * Description: 自定义弹框
 */

fun customDialog(dialog: BasePopupView, callback: XPopupCallback? = null): XPopup.Builder {
    val xPopupBuild = XPopup.Builder(dialog.context)
    xPopupBuild.isDestroyOnDismiss(true)
    xPopupBuild.setPopupCallback(callback)
    xPopupBuild.asCustom(dialog)
    dialog.show()
    return xPopupBuild
}

fun Activity.customDialog(dialog: BasePopupView, callback: XPopupCallback? = null): XPopup.Builder {
    val xPopupBuild = XPopup.Builder(this)
    xPopupBuild.isDestroyOnDismiss(true)
    xPopupBuild.setPopupCallback(callback)
    xPopupBuild.asCustom(dialog)
    dialog.show()
    return xPopupBuild
}

fun Fragment.customDialog(dialog: BasePopupView, callback: XPopupCallback? = null): XPopup.Builder {
    val xPopupBuild = XPopup.Builder(requireActivity())
    xPopupBuild.isDestroyOnDismiss(true)
    xPopupBuild.setPopupCallback(callback)
    xPopupBuild.asCustom(dialog)
    dialog.show()
    return xPopupBuild
}

/**
 * dialog 监听可根据需要添加
 */
abstract class SimpleXPopupCallback : XPopupCallback {
    override fun onCreated(popupView: BasePopupView) {}
    override fun beforeShow(popupView: BasePopupView) {}

    override fun onShow(popupView: BasePopupView) {}

    override fun onDismiss(popupView: BasePopupView) {}

    override fun beforeDismiss(popupView: BasePopupView) {}

    override fun onBackPressed(popupView: BasePopupView): Boolean {
        return false
    }

    override fun onKeyBoardStateChanged(popupView: BasePopupView, height: Int) {}

    override fun onDrag(popupView: BasePopupView, value: Int, percent: Float, upOrLeft: Boolean) {}

    override fun onClickOutside(popupView: BasePopupView) {}
}
