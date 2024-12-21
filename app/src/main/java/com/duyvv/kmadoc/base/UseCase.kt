package com.duyvv.kmadoc.base

import kotlinx.coroutines.flow.Flow

interface UseCase<in R, out T> {
    fun execute(value: R? = null): Flow<T>
}