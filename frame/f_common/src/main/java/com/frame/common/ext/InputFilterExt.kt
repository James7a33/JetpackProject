package com.frame.common.ext

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern


/**
 * 禁止输入空格
 */
class NoSpaces : InputFilter {
    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {
        return if (source!! == " " || source.toString().contentEquals("\n")) ""
        else null
    }
}

/**
 * 检测是否输入汉字
 */
class ChineseInput : InputFilter {
    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {
        source!!.forEachIndexed { _, chart ->
            if (!isChinese(chart)) {
                return ""
            }
        }
        return null
    }

    private fun isChinese(c: Char): Boolean {
        return c.code in 0x4E00..0x9FA5
    }
}

/**
 * 禁止输入@regex 以外的字符
 * @property regex 正则表达式
 */
class RegexInput(private val regex: String) : InputFilter {
    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
    ): CharSequence? {

        val newString = dest?.toString()?.substring(0, dstart) + source?.substring(
            start, end
        ) + dest?.toString()?.substring(dend)
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(newString)

        // 返回 null 表示允许输入，否则返回 "" 表示阻止输入
        return if (!matcher.matches()) "" else null
    }
}