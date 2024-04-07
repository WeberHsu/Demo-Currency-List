package com.weberhsu.base.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/**
 * author : weber
 * desc :
 */
interface DispatcherProvider {

    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

}