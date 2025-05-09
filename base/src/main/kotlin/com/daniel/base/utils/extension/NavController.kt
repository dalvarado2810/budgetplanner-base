package com.daniel.base.utils.extension

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.daniel.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.isActive

@SuppressLint("RestrictedApi")
inline fun <reified T : BaseViewModel<*, *, *>> NavController.viewModel(): T? {
    return visibleEntries.value.firstNotNullOfOrNull { backStackEntry ->
        backStackEntry.viewModelStore.let { viewModelStore ->
            viewModelStore.keys()
                .find { key -> key.endsWith("${T::class.qualifiedName}") }
                ?.let(viewModelStore::get)
        }
    } as? T
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("RestrictedApi")
fun NavController.viewModelFlow() =
    currentBackStackEntryFlow
        .filter { backStackEntry -> backStackEntry.destination.navigatorName != "dialog" }
        .mapLatest { backStackEntry ->
            val viewmodelStore = backStackEntry.viewModelStore

            viewmodelStore.keys()
                .mapNotNull { key -> viewmodelStore[key] }
                .filterIsInstance<BaseViewModel<*, *, *>>()
                .find { viewModel -> viewModel.viewModelScope.isActive }!!
        }
        .retry { throwable ->
            delay(100)
            throwable is NullPointerException
        }