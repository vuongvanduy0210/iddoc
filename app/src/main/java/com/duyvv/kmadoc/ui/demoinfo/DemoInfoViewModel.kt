package com.duyvv.kmadoc.ui.demoinfo

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.request.UpdateStatusFormRequest
import com.duyvv.kmadoc.data.dto.request.UploadFormRequest
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.data.usecase.CreateFormUseCase
import com.duyvv.kmadoc.data.usecase.DeleteFormUseCase
import com.duyvv.kmadoc.data.usecase.UpdateFormUseCase
import com.duyvv.kmadoc.data.usecase.UpdateStatusFormUseCase
import com.duyvv.kmadoc.data.usecase.UploadFormUseCase
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toContinueStudyRequest
import com.duyvv.kmadoc.util.toCreateStudentCardRequest
import com.duyvv.kmadoc.util.toDropOutRequest
import com.duyvv.kmadoc.util.toStudentHealthRequest
import com.duyvv.kmadoc.util.updateOCRFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DemoInfoViewModel @Inject constructor(
    private val updateStatusFormUseCase: UpdateStatusFormUseCase,
    private val uploadFormUseCase: UploadFormUseCase,
    private val updateFormUseCase: UpdateFormUseCase,
    private val deleteFormUseCase: DeleteFormUseCase,
    private val createFormUseCase: CreateFormUseCase
) : BaseViewModel() {

    private val _formModel = MutableStateFlow(FormModel())
    val formModel = _formModel.asStateFlow()

    var typeModel: FormTypeModel? = null

    fun updateData(fieldName: String, newContent: String) {
        _formModel.update {
            it.updateOCRFormData(fieldName, newContent)
        }
    }

    fun updateFormData(formModel: FormModel?) {
        if (formModel != null) {
            _formModel.value = formModel
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateStatusForm(formId: String, status: String) {
        showLoading(true)
        flowOf(UpdateStatusFormRequest(status)).flatMapLatest {
            updateStatusFormUseCase.execute(formId, it)
        }.onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(DemoInfoContract.DemoInfoEffect.UpdateStatusFormSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun uploadForm(adminId: String, categoryId: String) {
        showLoading(true)
        flowOf(getCreateFormRequest(typeModel?.type)).flatMapLatest {
            uploadFormUseCase.execute(
                adminId, UploadFormRequest(categoryId, status = "APPROVED", it)
            )
        }.onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(DemoInfoContract.DemoInfoEffect.UploadFormSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateForm(formId: String) {
        showLoading(true)
        flowOf(getCreateFormRequest(typeModel?.type)).flatMapLatest {
            updateFormUseCase.execute(formId, it)
        }.onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(DemoInfoContract.DemoInfoEffect.UpdateFormSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }

    fun deleteForm(formId: String) {
        showLoading(true)
        deleteFormUseCase.execute(formId)
            .onEachError {
                showLoading(false)
                showError(it)
            }.onEach {
                showLoading(false)
                sendEffect(DemoInfoContract.DemoInfoEffect.DeleteFormSuccess)
            }.launchIn(viewModelScopeExceptionHandler)
    }


    private fun getCreateFormRequest(type: FormType?): BaseCreateFormRequest {
        return when (type) {
            FormType.THOI_HOC -> {
                _formModel.value.toDropOutRequest()
            }

            FormType.CAP_LAI_THE_BHYT -> {
                _formModel.value.toStudentHealthRequest()
            }

            FormType.XIN_TIEP_TUC_HOC -> {
                _formModel.value.toContinueStudyRequest()
            }

            else -> {
                _formModel.value.toCreateStudentCardRequest()
            }
        }
    }

    fun createForm(formTypeModel: FormTypeModel) {
        showLoading(true)
        val userId = SharePreferenceExt.userInfo.userId
        createFormUseCase.execute(
            studentId = userId,
            formId = formTypeModel.id,
            body = getCreateFormRequest(formTypeModel.type)
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            sendEffect(DemoInfoContract.DemoInfoEffect.CreateFormSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }
}