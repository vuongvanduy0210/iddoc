package com.duyvv.kmadoc.ui.changeuserinfo.changepass

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.ChangePassRequest
import com.duyvv.kmadoc.data.usecase.ChangePasswordUseCase
import com.duyvv.kmadoc.ui.changeuserinfo.ChangeInfoContract
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePassUseCase: ChangePasswordUseCase
) : BaseViewModel() {

    private val _oldPassword = MutableStateFlow("")
    val oldPassword = _oldPassword.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    val isValidInput =
        combine(oldPassword, password, confirmPassword) { oldPassword, password, confirmPassword ->
            oldPassword.length >= 6 && password.length >= 6 && confirmPassword.length >= 6
        }

    fun updatePassword(pass: String) {
        _password.value = pass
    }

    fun updateConfirmPassword(pass: String) {
        _confirmPassword.value = pass
    }

    fun updateOldPassword(pass: String) {
        _oldPassword.value = pass
    }

    fun changePass() {
        val userId = SharePreferenceExt.userInfo.userId
        changePassUseCase.execute(
            userId,
            ChangePassRequest(
                lastPassword = _oldPassword.value,
                password = _password.value,
                retypePassword = _confirmPassword.value
            )
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(ChangeInfoContract.ChangeInfoEffect.ChangePasswordSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }
}