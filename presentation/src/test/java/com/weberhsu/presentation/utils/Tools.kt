package com.weberhsu.presentation.utils

import io.mockk.InternalPlatformDsl
import io.mockk.mockk

/**
 * author : weber
 * desc :
 */

fun Any.mockCallPrivateFunc(methodName: String, vararg elements: Any): Any? {
    return InternalPlatformDsl.dynamicCall(this, methodName, elements) { mockk() }
}

inline fun <reified T : Any, R> T.setPrivateProperty(name: String, value: R) {
    T::class.java.getDeclaredField(name).let {
        it.isAccessible = true
        it.set(this, value)
    }
}

/**
 * get property
 */
fun Any.mockGetProperty(name: String): Any? {
    return InternalPlatformDsl.dynamicGet(this, name)
}

/**
 * set property
 */
fun Any.mockSetProperty(name: String, value: Any?) {
    InternalPlatformDsl.dynamicSet(this, name, value)
}