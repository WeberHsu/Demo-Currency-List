package com.weberhsu.base.test

/**
 * author : weber
 * desc :
 */
data class TestException(
    override val message: String? = null
) : Exception(message)