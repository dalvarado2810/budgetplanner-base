package com.daniel.base.presentation.action

import com.daniel.base.presentation.ViewAction

interface ActionInterceptor<A: ViewAction> {
    suspend fun onIntercept(action: A)
}