package com.frame.common.ext


/**
 * 正则校验：校验密码
 * 规则：大小写字母数字组合，长度在8-20之间
 * @return true 符合规则，false 不符合规则
 */
fun String.regexPsd(): Boolean {
    val regex = "^[a-zA-Z0-9]{8,20}$"
    return matches(regex.toRegex())
}

/**
 * 正则校验：校验密码
 * 规则：至少包含大小写字母及数字中的一种
 * @return true 符合规则，false 不符合规则
 */
fun String.regexPsdLetterOrDigit(): Boolean {
    val regex = "^[a-zA-Z0-9]+$"
    return matches(regex.toRegex())
}

/**
 * 正则校验：校验密码
 * 规则：至少包含大小写字母及数字中的两种
 * @return true 符合规则，false 不符合规则
 */
fun String.regexPsdLetterDigit(): Boolean {
    val isDigit = any { it.isDigit() }
    val isLetter = any { it.isLetter() }
    val regex = "^[a-zA-Z0-9]{8,20}$"
    return isDigit && isLetter && matches(regex.toRegex())
}

/**
 * 正则校验：校验密码
 * 规则：必须同时包含大小写字母及数字
 * @return true 符合规则，false 不符合规则
 */
fun String.regexPsdContainThree(): Boolean {
    val isDigit = any { it.isDigit() }
    val isLowerCase = any { it.isLowerCase() }
    val isUpperCase = any { it.isUpperCase() }
    val regex = "^[a-zA-Z0-9]+$"
    return isDigit && isLowerCase && isUpperCase && matches(regex.toRegex())
}

/**
 * 正则校验：校验密码
 * 规则：必须同时包含大小写字母、数字和特殊符号，长度在8-20之间
 * @return true 符合规则，false 不符合规则
 */
fun String.regexPsdContainAll(): Boolean {
    val isDigit = any { it.isDigit() }
    val isLowerCase = any { it.isLowerCase() }
    val isUpperCase = any { it.isUpperCase() }
    val isSymbol = any { !it.isLetterOrDigit() }
    val regex = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{6,16}$"
    return isDigit && isLowerCase && isUpperCase && isSymbol && matches(regex.toRegex())
}

