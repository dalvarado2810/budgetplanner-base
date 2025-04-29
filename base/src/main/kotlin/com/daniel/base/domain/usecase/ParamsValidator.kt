package com.daniel.base.domain.usecase

interface ParamsValidator<P> {
    fun validate(params: P)
}