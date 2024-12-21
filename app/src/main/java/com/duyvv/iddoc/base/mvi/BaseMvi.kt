package com.duyvv.iddoc.base.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseMvi {
    val mviIntent: SharedFlow<MviIntent>
    val mviEffect: Flow<MviEffect>
    val mviState: StateFlow<MviState>
}