package com.tools.permission.interceptor

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.NonNull
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionInterceptor
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.PermissionFragmentFactory
import com.hjq.permissions.XXPermissions
import com.lxj.xpopup.XPopup
import com.tools.permission.R
import com.tools.toast.ext.toastLong

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XXPermissions
 * time   : 2021/01/04
 * desc   : 权限申请拦截器
 */
class PermissionInterceptor(private val permissionDesc: String) :
    OnPermissionInterceptor {
    private val HANDLER: Handler = Handler(Looper.getMainLooper())

    /** 权限申请标记  */
    private var mRequestFlag = false

    /**
     * 权限申请说明 Popup
     */
    private var mPermissionPopup: PopupWindow? = null

    override fun launchPermissionRequest(
        activity: Activity,
        fragmentFactory: PermissionFragmentFactory<*, *>,
        requestPermissions: MutableList<String>,
        callback: OnPermissionCallback?
    ) {
        mRequestFlag = true
        val deniedPermissions = XXPermissions.getDeniedPermissions(activity, requestPermissions)
        //如果权限被拒绝，不在进行权限提示，直接请求权限
        if (XXPermissions.isDoNotAskAgainPermissions(activity, requestPermissions)) {
            dispatchPermissionRequest(activity, requestPermissions, fragmentFactory, callback)
            return
        }
        val mPermissionDescription = if (permissionDesc.isEmpty()) {
            activity.getString(
                R.string.common_permission_message_normal,
                PermissionNameConvert.getPermissionString(activity, deniedPermissions)
            )
        } else {
            StringBuilder()
                .append(permissionDesc)
                .append(
                    activity.getString(
                        R.string.common_permission_message,
                        PermissionNameConvert.getPermissionString(activity, deniedPermissions)
                    )
                ).toString()
        }
        val decorView = activity.window.decorView as ViewGroup
        val activityOrientation = activity.resources.configuration.orientation
        var showPopupWindow = activityOrientation == Configuration.ORIENTATION_PORTRAIT
        for (permission in requestPermissions) {
            if (!XXPermissions.isSpecialPermission(permission)) {
                continue
            }
            if (XXPermissions.isGrantedPermissions(activity, permission)) {
                continue
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R &&
                TextUtils.equals(Permission.MANAGE_EXTERNAL_STORAGE, permission)
            ) {
                continue
            }
            // 如果申请的权限带有特殊权限，并且还没有授予的话
            // 就不用 PopupWindow 对话框来显示，而是用 Dialog 来显示
            showPopupWindow = false
            break
        }
        if (showPopupWindow) {
            dispatchPermissionRequest(activity, requestPermissions, fragmentFactory, callback)
            // 延迟 300 毫秒是为了避免出现 PopupWindow 显示然后立马消失的情况
            // 因为框架没有办法在还没有申请权限的情况下，去判断权限是否永久拒绝了，必须要在发起权限申请之后
            // 所以只能通过延迟显示 PopupWindow 来做这件事，如果 300 毫秒内权限申请没有结束，证明本次申请的权限没有永久拒绝
            HANDLER.postDelayed({
                if (!mRequestFlag) {
                    return@postDelayed
                }
                if (activity.isFinishing || activity.isDestroyed) {
                    return@postDelayed
                }
                showPopupWindow(activity, decorView, mPermissionDescription)
            }, 300)
        } else {
            // 注意：这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
            XPopup.Builder(activity).asConfirm(
                activity.getString(R.string.common_permission_description), mPermissionDescription
            ) {
                dispatchPermissionRequest(
                    activity,
                    requestPermissions,
                    fragmentFactory,
                    callback
                )
            }.show()
        }
    }

    override fun grantedPermissionRequest(
        activity: Activity, allPermissions: List<String>,
        grantedPermissions: List<String>, allGranted: Boolean,
        callback: OnPermissionCallback?
    ) {
        if (callback == null) {
            return
        }
        callback.onGranted(grantedPermissions, allGranted)
    }

    override fun deniedPermissionRequest(
        activity: Activity, allPermissions: List<String>,
        deniedPermissions: List<String>, doNotAskAgain: Boolean,
        callback: OnPermissionCallback?
    ) {
        callback?.onDenied(deniedPermissions, doNotAskAgain)
        if (doNotAskAgain) {
            if (deniedPermissions.size == 1 && Permission.ACCESS_MEDIA_LOCATION == deniedPermissions[0]) {
                R.string.common_permission_media_location_hint_fail.toastLong()
                return
            }
            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback)
            return
        }
        if (deniedPermissions.size == 1) {
            val deniedPermission = deniedPermissions[0]
            val backgroundPermissionOptionLabel = getBackgroundPermissionOptionLabel(activity)
            if (Permission.ACCESS_BACKGROUND_LOCATION == deniedPermission) {
                activity.getString(
                    R.string.common_permission_background_location_fail_hint,
                    backgroundPermissionOptionLabel
                ).toastLong()
                return
            }
            if (Permission.BODY_SENSORS_BACKGROUND == deniedPermission) {
                activity.getString(
                    R.string.common_permission_background_sensors_fail_hint,
                    backgroundPermissionOptionLabel
                ).toastLong()
                return
            }
        }

        val message: String
        val permissionNames = PermissionNameConvert.permissionsToNames(activity, deniedPermissions)
        message = if (permissionNames.isNotEmpty()) {
            activity.getString(
                R.string.common_permission_fail_assign_hint,
                PermissionNameConvert.listToString(activity, permissionNames)
            )
        } else {
            activity.getString(R.string.common_permission_fail_hint)
        }
        message.toastLong()
    }

    override fun finishPermissionRequest(
        activity: Activity, allPermissions: List<String>,
        skipRequest: Boolean, callback: OnPermissionCallback?
    ) {
        mRequestFlag = false
        dismissPopupWindow()
    }

    private fun showPopupWindow(activity: Activity, decorView: ViewGroup, message: String) {
        if (mPermissionPopup == null) {
            val contentView: View = LayoutInflater.from(activity).inflate(
                R.layout.layout_permission_description_popup,
                decorView, false
            )
            mPermissionPopup = PopupWindow(activity)
            mPermissionPopup!!.contentView = contentView
            mPermissionPopup!!.width = WindowManager.LayoutParams.MATCH_PARENT
            mPermissionPopup!!.height = WindowManager.LayoutParams.WRAP_CONTENT
            mPermissionPopup!!.animationStyle = android.R.style.Animation_Dialog
            mPermissionPopup!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mPermissionPopup!!.isTouchable = true
            mPermissionPopup!!.isOutsideTouchable = true
        }
        val messageView =
            mPermissionPopup!!.contentView.findViewById<TextView>(R.id.tv_permission_description_message)
        messageView.text = message
        // 注意：这里的 PopupWindow 只是示例，没有监听 Activity onDestroy 来处理 PopupWindow 生命周期
        mPermissionPopup!!.showAtLocation(decorView, Gravity.TOP, 0, 0)
    }

    private fun dismissPopupWindow() {
        if (mPermissionPopup == null) {
            return
        }
        if (!mPermissionPopup!!.isShowing) {
            return
        }
        mPermissionPopup!!.dismiss()
    }

    private fun showPermissionSettingDialog(
        activity: Activity?, allPermissions: List<String>,
        deniedPermissions: List<String>, callback: OnPermissionCallback?
    ) {
        if (activity == null || activity.isFinishing || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed) {
            return
        }
        var message: String? = null
        val permissionNames = PermissionNameConvert.permissionsToNames(activity, deniedPermissions)
        if (permissionNames.isNotEmpty()) {
            if (deniedPermissions.size == 1) {
                val deniedPermission = deniedPermissions[0]
                if (Permission.ACCESS_BACKGROUND_LOCATION == deniedPermission) {
                    message = activity.getString(
                        R.string.common_permission_manual_assign_fail_background_location_hint,
                        getBackgroundPermissionOptionLabel(activity)
                    )
                } else if (Permission.BODY_SENSORS_BACKGROUND == deniedPermission) {
                    message = activity.getString(
                        R.string.common_permission_manual_assign_fail_background_sensors_hint,
                        getBackgroundPermissionOptionLabel(activity)
                    )
                }
            }
            if (TextUtils.isEmpty(message)) {
                message = activity.getString(
                    R.string.common_permission_manual_assign_fail_hint,
                    PermissionNameConvert.listToString(activity, permissionNames)
                )
            }
        } else {
            message =
                activity.getString(R.string.common_permission_manual_fail_hint)
        }

        XPopup.Builder(activity)
            .asConfirm(
                activity.getString(R.string.common_permission_alert),
                message
            ) {
                XXPermissions.startPermissionActivity(
                    activity,
                    deniedPermissions, object : OnPermissionPageCallback {
                        override fun onGranted() {
                            if (callback == null) {
                                return
                            }
                            callback.onGranted(allPermissions, true)
                        }

                        override fun onDenied() {
                            showPermissionSettingDialog(
                                activity,
                                allPermissions,
                                XXPermissions.getDeniedPermissions(activity, allPermissions),
                                callback
                            )
                        }
                    })
            }.show()

    }

    /**
     * 获取后台权限的《始终允许》选项的文案
     */
    private fun getBackgroundPermissionOptionLabel(context: Context): String {
        var backgroundPermissionOptionLabel = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            backgroundPermissionOptionLabel =
                context.packageManager.backgroundPermissionOptionLabel.toString()
        }
        if (TextUtils.isEmpty(backgroundPermissionOptionLabel)) {
            backgroundPermissionOptionLabel =
                context.getString(R.string.common_permission_background_default_option_label)
        }
        return backgroundPermissionOptionLabel
    }
}