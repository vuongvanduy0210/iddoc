package com.duyvv.iddoc.util

typealias Action = () -> Unit
typealias TypeAction<T> = (T) -> Unit
typealias sTypeAction<T> = suspend (T) -> Unit


typealias Receiver<T> = (T).() -> Unit