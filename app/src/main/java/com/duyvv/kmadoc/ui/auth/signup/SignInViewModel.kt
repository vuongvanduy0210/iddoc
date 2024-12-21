package com.duyvv.kmadoc.ui.auth.signup

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.SignUpRequest
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.usecase.StudentSignUpUseCase
import com.duyvv.kmadoc.util.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val studentSignUpUseCase: StudentSignUpUseCase
) : BaseViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _studentCode = MutableStateFlow("")
    val studentCode = _studentCode.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    val isValidInput = combine(
        userName, studentCode, password, confirmPassword
    ) { _ ->
        userName.value.isNotEmpty() && studentCode.value.isNotEmpty() && password.value.length >= 6 && confirmPassword.value.length >= 6
    }

    private val _signUpResponse = MutableStateFlow<SignUpResponse?>(null)
    val signUpResponse = _signUpResponse.asStateFlow()

    fun signUp() {
        if (password.value.trim() != confirmPassword.value.trim()) {
            showError(Throwable("Mật khẩu không trùng khớp"))
            return
        }
        showLoading(true)
        studentSignUpUseCase.execute(
            SignUpRequest(
                studentCode = studentCode.value.trim(),
                password = password.value.trim(),
                username = userName.value.trim()
            )
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            _signUpResponse.value = it
        }.launchIn(viewModelScopeExceptionHandler)
    }

    fun updateUserName(name: String) {
        _userName.value = name
    }

    fun updateStudentCode(code: String) {
        _studentCode.value = code
    }

    fun updatePassword(pass: String) {
        _password.value = pass
    }

    fun updateConfirmPassword(pass: String) {
        _confirmPassword.value = pass
    }
}