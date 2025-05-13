package com.daniel.base.utils.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.daniel.base.presentation.model.DoNotThrottle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.reflect.full.hasAnnotation

internal fun <T>Flow<T>.throttle(durationMillis: Long): Flow<T> = flow {
    var lastEmissionTime = 0L

    collect { value ->
        val kClass = value?.let { it::class }

        if (kClass != null && kClass.hasAnnotation<DoNotThrottle>()) {
            emit(value)
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmissionTime > durationMillis) {
                lastEmissionTime = currentTime
                emit(value)
            }
        }
    }
}

@Composable
inline fun <reified T> CollectEffectWithLifecycle(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        withContext(Dispatchers.Main.immediate) {
            flow
                .flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
                .collect(action)
        }
    }
}