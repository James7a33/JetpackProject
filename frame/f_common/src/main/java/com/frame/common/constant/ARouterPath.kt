package com.frame.common.constant

/**
 * @Author: james
 * @Date: 2024/3/19 10:05
 * @Description:路由配置
 * 命名规则：/开头并且必须大于两级，/模块/分类/具体名称
 * 比如: /模块名称/组件[activity/fragment/service]/组件名称
 */
object ARouterPath {

    object App {
        //开屏页/闪屏页
        const val SPLASH = "/app/activity/SplashActivity"
    }

    object Main {
        //主页
        const val MAIN = "/main/activity/MainActivity"
    }

    object Web {
        //内置浏览器
        const val BROWSER = "/web/activity/BrowserActivity"
    }

    object Login {
        //登录页
        const val LOGIN = "/login/activity/LoginActivity"

    }

    object Mine {
        //登录页
        const val SETTING = "/mine/activity/SettingActivity"
        const val VERIFIED = "/mine/activity/VerifiedActivity"
        const val PERSONAL = "/mine/activity/PersonalInfoActivity"
        const val EDITOR = "/mine/activity/PersonalEditInfomationActivity"
        const val WALLET = "/mine/activity/PersonalWalletActivity"
        const val VISITOR = "/mine/activity/PersonalVisitorActivity"
        const val FANS = "/mine/activity/PersonalFansActivity"
        const val FOLLOW = "/mine/activity/PersonalFollowActivity"
    }

    object Room {
        //语音直播
        const val VOICE_LIVE_ROOM = "/room/activity/LiveRoomVoiceActivity"

        //视频直播
        const val VIDEO_LIVE_ROOM = "/room/activity/LiveRoomVideoActivity"
    }
}