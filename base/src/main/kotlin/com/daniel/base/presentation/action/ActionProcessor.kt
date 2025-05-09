package com.daniel.base.presentation.action

import com.daniel.base.presentation.Mutation
import com.daniel.base.presentation.ViewAction
import com.daniel.base.presentation.ViewEffect
import com.daniel.base.presentation.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

abstract class ActionProcessor<S: ViewState, A: ViewAction, E: ViewEffect> {
    open fun process(
        action: A,
        sideEffect: (E) -> Unit
    ): Flow<Mutation<S>> = flowOf { state -> state }
}