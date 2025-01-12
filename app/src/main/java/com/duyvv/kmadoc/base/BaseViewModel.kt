package com.duyvv.kmadoc.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyvv.kmadoc.base.mvi.BaseMvi
import com.duyvv.kmadoc.base.mvi.Initialize
import com.duyvv.kmadoc.base.mvi.MviEffect
import com.duyvv.kmadoc.base.mvi.MviIntent
import com.duyvv.kmadoc.base.mvi.MviState
import com.duyvv.kmadoc.util.DEFAULT_ERROR_MESSAGE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

open class BaseViewModel : ViewModel(), BaseMvi {

    val isLoading = Channel<Boolean>()
    val errorMessage = Channel<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    private val _mviIntent = MutableSharedFlow<MviIntent>(
        0,
        1,
        BufferOverflow.SUSPEND
    )
    final override val mviIntent: SharedFlow<MviIntent>
        get() = _mviIntent.asSharedFlow()

    private val _mviEffect = Channel<MviEffect>(Channel.BUFFERED)
    override val mviEffect: Flow<MviEffect>
        get() = _mviEffect.receiveAsFlow()

    private val _mviState = MutableStateFlow<MviState>(Initialize)
    override val mviState: StateFlow<MviState>
        get() = _mviState.asStateFlow()

    val viewModelScopeExceptionHandler by lazy {
        viewModelScope + exceptionHandler
    }

    init {
        mviIntent.onEach {
            handleIntent(it)
        }.catch {}
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScopeExceptionHandler)
    }

    fun setState(state: MviState) {
        _mviState.value = state
    }

    fun sendEffect(effect: MviEffect) {
        viewModelScopeExceptionHandler.launch { _mviEffect.send(effect) }
    }

    fun onEvent(intent: MviIntent) {
        viewModelScopeExceptionHandler.launch { _mviIntent.emit(intent) }
    }

    open fun handleIntent(intent: MviIntent) {}

    fun showLoading(isShow: Boolean) {
        viewModelScopeExceptionHandler.launch {
            isLoading.send(isShow)
        }
    }

    fun showError(throwable: Throwable) {
        viewModelScopeExceptionHandler.launch {
            errorMessage.send(throwable.message ?: DEFAULT_ERROR_MESSAGE)
        }
    }

    private fun handleException(throwable: Throwable) {
        showError(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScopeExceptionHandler.cancel()
    }
}