package com.daniel.base.domain.usecase

abstract class ExceptionHandler<E: UseCaseError> {
    protected open fun parseException(throwable: Throwable): E? = null

    suspend fun reportException(throwable: Throwable): E? {
        val error = parseException(throwable)

        return error
    }
}