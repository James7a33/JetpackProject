package com.frame.common.ext

import android.widget.EditText


/**
 * 正则校验：校验用户名
 */
fun String.regexUserName(): Boolean {
    val regex = "^[a-zA-Z]\\w{5,17}$"
    return matches(regex.toRegex())
}



/**
 * 正则校验：校验手机号
 * 规则：1 开头，第二位为3-9，后面11位为数字
 */
fun String.regexPhoneNum(): Boolean {
    val regex = "^(1(3[0-9]|4[5-9]|5[0-35-9]|6[6]|7[0-8]|8[0-9]|9[0-9]))\\d{8}$"
    return matches(regex.toRegex())
}

/**
 * 正则校验：校验邮箱
 * 规则：字母开头，字母数字下划线@字母域名
 */
fun String.regexEmail(): Boolean {
    val regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    return matches(regex.toRegex())
}

/**
 * 正则校验：校验汉字
 * 规则：汉字
 */
fun String.regexChinese(): Boolean {
    val regex = "^[\u4e00-\u9fa5]+$"
    return matches(regex.toRegex())
}

/**
 *  正则校验：校验身份证
 *  规则：15位或18位
 */
fun String.regexIDCard(): Boolean {
    val regex = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)"
    return matches(regex.toRegex())
}

/**
 * 正则校验：根据身份证号判断性别
 * 规则：身份证
 */
fun String.regexIDCardSex(): String {
    var sexStr = "0"
    if (length == 15) {
        sexStr = substring(14, 15)
    } else if (length == 18) {
        sexStr = substring(16, 17)
    }
    val sexNo = sexStr.toIntOrNull() ?: 0
    return if (sexNo % 2 == 0) "女" else "男"
}

/**
 *  正则校验：校验URL
 *  规则：http://www.baidu.com
 */
fun String.regexUrl(): Boolean {
    val regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"
    return matches(regex.toRegex())
}

/**
 *  正则校验：校验IP地址
 *  规则：xxx.xxx.xxx.xxx
 */
fun String.regexIPAddress(): Boolean {
    // IP 地址正则表达式需要匹配完整的 IPv4 格式
    val regex = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})){3}$"
    return matches(regex.toRegex())
}

