package com.weberhsu.presentation

import androidx.lifecycle.viewModelScope
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.usecase.CurrencyUseCase
import com.weberhsu.presentation.base.BaseViewModel
import com.weberhsu.presentation.base.MviModel
import com.weberhsu.presentation.base.MviModelHost
import com.weberhsu.presentation.base.statemachine.StateMachine
import com.weberhsu.presentation.contract.MainEvent
import com.weberhsu.presentation.contract.MainSideEffect
import com.weberhsu.presentation.contract.MainState
import com.weberhsu.presentation.contract.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val useCase: CurrencyUseCase
) : MviModelHost<MainState, MainSideEffect>, BaseViewModel() {

    override val model: MviModel<MainState, MainSideEffect> = model(viewModelScope, MainState.Init)

    init {
        stateMachine = stateMachine.with {
            onTransition {
                val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
                val event = validTransition.event
                when (val state = validTransition.toState) {
                    MainState.Loading -> {
                        when (event) {
                            MainEvent.Clear -> clearData()
                            MainEvent.Insert -> insertDataIntoDb()
                            is MainEvent.Search -> onSearchValueChanged(event.text)
                            MainEvent.ShowCryptos -> showCryptos()
                            MainEvent.ShowFiats -> showFiats()
                            else -> {}
                        }
                    }

                    is MainState.CurrencyList -> {
                        if (event is MainEvent.ShowDataSuccess) {
                            state.run {
                                isCryptoCurrency = isShowCrypto
                                currencies = event.currencies
                            }
                            handleEvent(MainEvent.Done)
                        }
                    }

                    is MainState.Error -> {
                        if (event is MainEvent.ToError) {
                            state.errMsg = event.errMsg
                        }
                        intent {
                            sendEffect(MainSideEffect.ShowError(state.errMsg ?: ""))
                        }
                        handleEvent(MainEvent.Done)
                    }

                    else -> {}
                }

                intent {
                    println(validTransition.toState)
                    state { validTransition.toState }
                }
            }
        }
    }

    private var searchJob: Job? = null
    private var isShowCrypto: Boolean = true
    private var lastSearch: String? = null

    fun handleEvent(event: MainEvent) {
        stateMachine.transition(event)
    }

    /**
     * Insert the data into the local database
     */
    private fun insertDataIntoDb() {
        viewModelScope.launch {
            useCase.insertInitialData().handleResultEvent()
        }
    }


    /**
     * Clearing the data in the local database
     */
    private fun clearData() {
        viewModelScope.launch {
            useCase.clearData().handleResultEvent()
        }
    }

    /**
     * Get all crypto list of [CurrencyInfo]
     */
    private fun showCryptos() {
        isShowCrypto = true
        onSearchValueChanged(lastSearch)
    }

    /**
     * Get all fiat list of [CurrencyInfo]
     */
    private fun showFiats() {
        isShowCrypto = false
        onSearchValueChanged(lastSearch)
    }

    private fun onSearchValueChanged(text: String?) {
        searchJob?.cancel()
        searchJob = null
        lastSearch = text

        searchJob = viewModelScope.launch {
            handleCurrencyList(
                if (isShowCrypto) {
                    if (text?.isNotBlank() == true) {
                        useCase.searchCryptoCurrencies(text).first()
                    } else {
                        useCase.getCryptoCurrencies().first()
                    }
                } else {
                    if (text?.isNotBlank() == true) {
                        useCase.searchFiatCurrencies(text).first()
                    } else {
                        useCase.getFiatCurrencies().first()
                    }
                }
            )
            searchJob = null
        }
    }

    private fun Result<Any>.handleResultEvent() {
        this.onSuccess {
            // Clear & Insert Success then show currency list
            onSearchValueChanged(lastSearch)
        }.onFailure {
            handleErrorEvent(it)
        }
    }

    private fun handleCurrencyList(result: Result<List<CurrencyInfo>>): Result<List<CurrencyInfo>> {
        return result.onSuccess {
            handleEvent(MainEvent.ShowDataSuccess(it))
        }.onFailure {
            handleErrorEvent(it)
        }
    }

    private fun handleErrorEvent(e: Throwable) {
        handleEvent(MainEvent.ToError(e.message ?: "Error !"))
    }
}