package com.business.common.ext

import com.business.common.constant.Constants
import com.business.common.constant.mmUser
import com.frame.common.ext.toEntity
import com.frame.common.ext.toJsonStr
import com.tools.db.bean.UserInfoBean


/**
 * @Author: zzw
 * @Date: 2023/8/1 14:39
 * @Description:用户信息
 */

/**
 * 用户token 信息
 */
fun getUserToken(): String {
    return mmUser.getString(Constants.User.USER_TOKEN, "") ?: ""
}

/**
 * 设置用户token 信息
 */
fun setUserToken(token: String) {
    mmUser.putString(Constants.User.USER_TOKEN, token)
}

/**
 * 获取用户信息
 */
fun getUserInfo(): UserInfoBean? {
    val userStr = mmUser.getString(Constants.User.USER_INFO, "") ?: ""
    return userStr.toEntity<UserInfoBean>()
}

/**
 * 存储用户信息
 */
fun setUserInfo(userInfoBean: UserInfoBean?) {
    if (userInfoBean == null) {
        mmUser.putString(Constants.User.USER_INFO, "")
    } else {
        mmUser.putString(Constants.User.USER_INFO, userInfoBean.toJsonStr())
    }
}

/**
 * 获取用户Id
 */
fun getUserId(): String {
    val userInfo = getUserInfo()
    return if (userInfo != null) {
        return userInfo.id
    } else {
        ""
    }
}

/**
 * 是否已经登录
 */
fun isLogin(): Boolean {
    return mmUser.getBoolean(Constants.User.IS_LOGIN, false)
}

/**
 * 设置是否已经登录
 */
fun setIsLogin(isLogin: Boolean) {
    mmUser.putBoolean(Constants.User.IS_LOGIN, isLogin)
}