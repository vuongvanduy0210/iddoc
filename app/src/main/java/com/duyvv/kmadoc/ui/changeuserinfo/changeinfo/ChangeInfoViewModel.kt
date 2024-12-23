package com.duyvv.kmadoc.ui.changeuserinfo.changeinfo

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.ChangeAdminInfoRequest
import com.duyvv.kmadoc.data.dto.request.ChangeStudentInfoRequest
import com.duyvv.kmadoc.data.usecase.ChangeAdminInfoUseCase
import com.duyvv.kmadoc.data.usecase.ChangeStudentInfoUseCase
import com.duyvv.kmadoc.data.usecase.GetAdminInfoUseCase
import com.duyvv.kmadoc.data.usecase.GetStudentInfoUseCase
import com.duyvv.kmadoc.ui.changeuserinfo.ChangeInfoContract
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangeInfoViewModel @Inject constructor(
    private val changeStudentInfoUseCase: ChangeStudentInfoUseCase,
    private val changeAdminInfoUseCase: ChangeAdminInfoUseCase,
    private val getStudentInfoUseCase: GetStudentInfoUseCase,
    private val getAdminInfoUseCase: GetAdminInfoUseCase
) : BaseViewModel() {

    private val _userName = MutableStateFlow(SharePreferenceExt.userInfo.username)
    val userName = _userName.asStateFlow()

    private val _userInfo = MutableStateFlow("")
    val userInfo = _userInfo.asStateFlow()

    fun updateUserName(name: String) {
        _userName.value = name
    }

    fun updateStudentCode(code: String) {
        _userInfo.value = code
    }

    fun getStudentInfo() {
        showLoading(true)
        val studentId = SharePreferenceExt.userInfo.userId
        getStudentInfoUseCase.execute(studentId).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            _userInfo.value = it.info?.studentCode ?: ""
            _userName.value = it.info?.username ?: ""
            sendEffect(ChangeInfoContract.ChangeInfoEffect.GetStudentInfoSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    fun getAdminInfo() {
        showLoading(true)
        val adminId = SharePreferenceExt.userInfo.userId
        getAdminInfoUseCase.execute(adminId).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            _userInfo.value = it.info?.email ?: ""
            _userName.value = it.info?.username ?: ""
            sendEffect(ChangeInfoContract.ChangeInfoEffect.GetAdminInfoSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    fun updateStudentInfo(studentId: String) {
        showLoading(true)
        changeStudentInfoUseCase.execute(
            studentId,
            ChangeStudentInfoRequest(
                studentCode = userInfo.value,
                username = _userName.value
            )
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(ChangeInfoContract.ChangeInfoEffect.ChangeInfoSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    fun updateAdminInfo(adminId: String) {
        showLoading(true)
        changeAdminInfoUseCase.execute(
            adminId,
            ChangeAdminInfoRequest(email = userInfo.value, username = userName.value)
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(ChangeInfoContract.ChangeInfoEffect.ChangeInfoSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }
}