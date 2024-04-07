package com.weberhsu.presentation.extensions

import android.view.View
import android.widget.EditText
import androidx.core.view.SoftwareKeyboardControllerCompat

/**
 * author : weber
 * desc :
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

/**
 * 开启键盘
 */
fun EditText.showSoftKeyboard() {
    SoftwareKeyboardControllerCompat(this).show()
}

/**
 * 关闭键盘
 */
fun View.hideSoftKeyboard() {
    SoftwareKeyboardControllerCompat(this).hide()
}