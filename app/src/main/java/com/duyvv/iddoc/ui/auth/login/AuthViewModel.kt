package com.duyvv.iddoc.ui.auth.login

import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.data.dto.request.AdminLoginRequest
import com.duyvv.iddoc.data.dto.request.StudentLoginRequest
import com.duyvv.iddoc.data.dto.response.LoginResponse
import com.duyvv.iddoc.data.usecase.AdminLoginUseCase
import com.duyvv.iddoc.data.usecase.StudentLoginUseCase
import com.duyvv.iddoc.ui.auth.AuthContract
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val studentLoginUseCase: StudentLoginUseCase,
    private val adminLoginUseCase: AdminLoginUseCase
) : BaseViewModel() {
    private val _userName = MutableStateFlow(SharePreferenceExt.username)
    val userName = _userName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isAdminLogin = MutableStateFlow(false)
    val isAdminLogin = _isAdminLogin.asStateFlow()

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse = _loginResponse.asStateFlow()

    var isValidateInput = combine(
        _userName, _password
    ) { userName, password ->
        userName.isNotEmpty() && password.length >= 6
    }

    fun updateUserName(name: String) {
        _userName.value = name
    }

    fun updatePassword(pass: String) {
        _password.value = pass
    }

    fun updateIsAdminLogin(isAdmin: Boolean) {
        _isAdminLogin.value = isAdmin
    }

    fun login() {
        showLoading(true)
        if (isAdminLogin.value) {
            adminLoginUseCase.execute(
                AdminLoginRequest(
                    _userName.value.trim(),
                    _password.value.trim()
                )
            ).onEachError {
                showLoading(false)
                showError(it)
            }.onEach {
                showLoading(false)
                _loginResponse.value = it
                sendEffect(AuthContract.AuthEffect.LoginSuccess(it))
            }.launchIn(viewModelScopeExceptionHandler)
        } else {
            studentLoginUseCase.execute(
                StudentLoginRequest(
                    _userName.value.trim(),
                    _password.value.trim()
                )
            ).onEachError {
                showLoading(false)
                showError(it)
            }.onEach {
                showLoading(false)
                _loginResponse.value = it
                sendEffect(AuthContract.AuthEffect.LoginSuccess(it))
            }.launchIn(viewModelScopeExceptionHandler)
        }
    }
}