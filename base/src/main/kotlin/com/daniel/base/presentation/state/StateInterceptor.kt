package com.daniel.base.presentation.state

import com.daniel.base.presentation.ViewState

interface StateInterceptor<S : ViewState> {
    suspend fun onIntercept(state: S)
}