package com.business.common.constant

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

    }

    object Room {

    }
}