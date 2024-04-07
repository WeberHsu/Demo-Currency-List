package com.weberhsu.presentation.contract

import com.weberhsu.presentation.base.statemachine.StateMachine

/**
 * author : weber
 * desc : State flow about currency info list
 */
var stateMachine = StateMachine.create<MainState, MainEvent> {
    initialState(MainState.Init)
    state<MainState.Init> {
        on<MainEvent.Insert> {
            transitionTo(MainState.Loading)
        }
        on<MainEvent.Clear> {
            transitionTo(MainState.Loading)
        }
        on<MainEvent.Search> {
            transitionTo(MainState.Loading)
        }
        on<MainEvent.ShowCryptos> {
            transitionTo(MainState.Loading)
        }
        on<MainEvent.ShowFiats> {
            transitionTo(MainState.Loading)
        }
    }
    state<MainState.Loading> {
        on<MainEvent.ToError> {
            transitionTo(MainState.Error())
        }
        on<MainEvent.ShowDataSuccess> {
            transitionTo(MainState.CurrencyList())
        }
        on<MainEvent.ActionSuccess> {
            transitionTo(MainState.Init)
        }
    }
    state<MainState.Error> {
        on<MainEvent.Done> {
            transitionTo(MainState.Init)
        }
    }
    state<MainState.CurrencyList> {
        on<MainEvent.Done> {
            transitionTo(MainState.Init)
        }
    }
}