package com.frame.common.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.frame.base.appContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 打开键盘
 */
fun EditText.openKeyboard() {
    CoroutineScope(Dispatchers.Main).launch {
        delay(300)

        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()

        val imm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this@openKeyboard, InputMethodManager.SHOW_IMPLICIT)
        setSelection(length())
    }
}

/**
 * 关闭键盘
 */
fun EditText.hideKeyboard() {
    val imm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(
        this.windowToken,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

