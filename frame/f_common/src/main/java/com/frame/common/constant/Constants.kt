package com.frame.common.constant

import com.tencent.mmkv.MMKV


val mmUser: MMKV by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    MMKV.mmkvWithID(Constants.App.MMKV_USER_ID)
}

val mmAPP: MMKV by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    MMKV.mmkvWithID(Constants.App.MMKV_APP_ID)
}

object Constants {

    /**
     * 应用层级
     */
    object App {
        /*欢迎页*/
        const val GUIDE: String = "guide"

        /*app 连续返回退出*/
        const val APP_QUICK_TIME: Int = 2 * 1000

        /*mmkv 用户信息*/
        const val MMKV_USER_ID: String = "mmkv_user_id"

        /*mmkv app信息*/
        const val MMKV_APP_ID: String = "mmkv_app_id"

        /*点击时间间隔 */
        const val CLICK_TIME_INTERVAL = 300L

        /*打卡时间 保存的是周信息*/
        const val CHECK_IN_WEEKLY_REMINDER = "check_in_weekly_reminder"
    }

    /**
     * 用户层级
     */
    object User {
        //登录token
        const val LOGIN_TOKEN: String = "login_token"

        //用户 token
        const val USER_TOKEN: String = "user_token"

        //用户信息
        const val USER_INFO: String = "user_info"

        //用户 id
        const val USER_ID: String = "user_id"

        //是否登录
        const val IS_LOGIN: String = "is_login"
    }


}