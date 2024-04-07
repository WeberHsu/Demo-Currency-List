package com.weberhsu.presentation.contract

import com.weberhsu.presentation.base.SideEffect

/**
 * author : weber
 * desc :
 */
sealed class MainSideEffect : SideEffect {
    data class ShowError(val msg: String) : MainSideEffect()
}