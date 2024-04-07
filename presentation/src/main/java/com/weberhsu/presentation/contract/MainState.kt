package com.weberhsu.presentation.contract

import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.presentation.base.State

/**
 * author : weber
 * desc :
 */
sealed class MainState : State {
    object Init : MainState()
    object Loading : MainState()
    class Error : MainState() {
        var errMsg: String? = null
    }

    class CurrencyList : MainState() {
        var isCryptoCurrency: Boolean = true
        var currencies: List<CurrencyInfo>? = null
    }
}