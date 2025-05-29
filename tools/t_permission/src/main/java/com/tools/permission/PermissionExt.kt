package com.tools.permission

import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.tools.logger.logD
import com.tools.permission.interceptor.PermissionInterceptor

/**
 * 检测权限
 * @param context 上下文
 * @param permissions 权限
 * @param permissionDesc 功能描述
 * @param callback isSuccess 成功 isCheck 拒绝
 */
fun ckPermission(
    context: Context,
    vararg permissions: String,
    permissionDesc: String = "",
    callback: (isSuccess: Boolean) -> Unit
) {
    if (!XXPermissions.isGrantedPermissions(context, *permissions)) {
        XXPermissions.with(context)
            .permission(permissions)
            .interceptor(PermissionInterceptor(permissionDesc))
            .request(object : OnPermissionCallback {
                /**
                 * 有权限被同意授予时回调
                 *
                 * @param requestPermissions    请求成功的权限组
                 * @param allGranted            是否全部授予了
                 */
                override fun onGranted(
                    requestPermissions: MutableList<String>,
                    allGranted: Boolean
                ) {
                    permissions.forEach { permission ->
                        "请求权限${permission}".logD("ckPermission")
                    }
                    //判判断一个或多个权限是否全部授予了
                    callback.invoke(XXPermissions.isGrantedPermissions(context, permissions))
                }

                /**
                 * 有权限被拒绝授予时回调
                 *
                 * @param permissions            请求失败的权限组
                 * @param doNotAskAgain          是否勾选了不再询问选项
                 */
                override fun onDenied(
                    permissions: MutableList<String>, doNotAskAgain: Boolean
                ) {
                    super.onDenied(permissions, doNotAskAgain)
                    permissions.forEach { permission ->
                        "请求权限${permission} 失败， 切是否勾选了不在询问 ${if (doNotAskAgain) "是" else "否"}"
                            .logD("ckPermission")
                    }
                    //判判断一个或多个权限是否全部授予了
                    callback.invoke(XXPermissions.isGrantedPermissions(context, permissions))
                }
            })
    } else {
        callback.invoke(XXPermissions.isGrantedPermissions(context, permissions))
    }
}
