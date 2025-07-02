package com.daniel.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.base.presentation.Mutation
import com.daniel.base.presentation.ViewAction
import com.daniel.base.presentation.ViewEffect
import com.daniel.base.presentation.ViewState
import com.daniel.base.presentation.action.ActionInterceptor
import com.daniel.base.presentation.state.StateInterceptor
import com.daniel.base.utils.extension.throttle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<S: ViewState, A: ViewAction, E: ViewEffect>(
    initialState: S,
    initialAction: A? = null
) : ViewModel() {
    protected open val stateInterceptor: StateInterceptor<S>? = null
    protected open val actionInterceptor: ActionInterceptor<A>? = null

    private val actionChannel = Channel<A>()
    private val effectChannel = Channel<E>()

    val effect = effectChannel.receiveAsFlow()

    val state = actionChannel
        .receiveAsFlow()
        .onStart { if (initialAction != null) emit(initialAction) }
        .throttle(500)
        .onEach { action ->
            viewModelScope.launch { actionInterceptor?.onIntercept(action) }
        }
        .flatMapMerge { action -> processAction(action, ::sendEffect) }
        .scan(initialState) { currentState, mutation -> mutation(currentState)}
        .distinctUntilChanged()
        .onEach { state ->
            viewModelScope.launch { stateInterceptor?.onIntercept(state) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = initialState
        )

    protected abstract fun processAction(
        action: A,
        sideEffect: (E) -> Unit
    ): Flow<Mutation<S>>

    protected fun sendAction(viewAction: A) {
        viewModelScope.launch {
            actionChannel.send(viewAction)
        }
    }

    protected fun sendEffect(vieweffect: E) {
        viewModelScope.launch {
            effectChannel.send(vieweffect)
        }
    }
}