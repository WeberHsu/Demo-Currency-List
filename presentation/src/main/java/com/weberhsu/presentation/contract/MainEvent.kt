package com.weberhsu.presentation.contract

import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.presentation.base.Event

/**
 * author : weber
 * desc :
 */
sealed class MainEvent : Event {
    object Clear : MainEvent()
    object Insert : MainEvent()
    data class ToError(val errMsg: String) : MainEvent()
    object Done : MainEvent()
    data class Search(val text: String?) : MainEvent()
    object ShowCryptos : MainEvent()
    object ShowFiats : MainEvent()
    object ActionSuccess : MainEvent()
    data class ShowDataSuccess(val currencies: List<CurrencyInfo>) : MainEvent()
}