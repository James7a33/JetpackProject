package com.frame.common.ext

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import com.tools.toast.ext.toastShort

/**
 * 只抽取 afterTextChanged 方法
 */
fun EditText.afterTextChange(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

/**
 * 禁止edittext 点击
 */
fun EditText.noEdit() {
    noEdits(this)
}

/**
 * 禁止edittext 点击
 *
 * @param edit 多个
 */
fun EditText.noEdits(vararg edit: EditText) {
    edit.forEach {
        noEdits(it)
    }
}

/**
 * 禁止 edittext 点击
 * 事件处理
 * @param edit 多个
 */
fun EditText.noEdits(edit: EditText) {
    edit.apply {
        isFocusable = false
        isFocusableInTouchMode = false
        isCursorVisible = false
        inputType = InputType.TYPE_NULL
    }
}


/**
 * 判断输入内容类型（数字、字母、汉字）
 */
fun EditText.whatIsInput() {
    val txt = text.toString()

    if (txt.matches(Regex("[0-9]*"))) {
        "输入的是数字".toastShort()
    } else if (txt.matches(Regex("[a-zA-Z]"))) {
        "输入的是字母".toastShort()
    } else if (txt.matches(Regex("[\u4e00-\u9fa5]"))) {
        "输入的是汉字".toastShort()
    }
}

