package com.daniel.base.utils.extension

import com.daniel.base.presentation.model.DoNotThrottle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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