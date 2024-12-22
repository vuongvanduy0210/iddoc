package com.duyvv.kmadoc.ui.createform.forminfo

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.model.CreateFormModel
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.data.usecase.CreateFormUseCase
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toBaseCreateFormRequest
import com.duyvv.kmadoc.util.toContinueStudyRequest
import com.duyvv.kmadoc.util.toDropOutRequest
import com.duyvv.kmadoc.util.toStudentHealthRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateFormViewModel @Inject constructor(
    private val createFormUseCase: CreateFormUseCase
) : BaseViewModel() {

    val createFormModel = MutableStateFlow(CreateFormModel())

    private val isValidateBaseInfo = createFormModel.map {
        it.fullName.isNotEmpty() && it.phoneNumber.isNotEmpty() && it.major.isNotEmpty()
                && it.mClass.isNotEmpty() && it.studentId.isNotEmpty()
                && it.birthday.isNotEmpty()
                && it.gender.isNotEmpty() && it.address.isNotEmpty() && it.formDate.isNotEmpty()
    }

    fun isValidateInput(type: FormType?) = when (type) {
        FormType.THOI_HOC -> {
            combine(isValidateBaseInfo, isValidateDropOut) { baseInfo, dropOut ->
                baseInfo && dropOut
            }
        }

        FormType.CAP_LAI_THE_BHYT -> {
            combine(isValidateBaseInfo, isValidateBHYT) { baseInfo, dropOut ->
                baseInfo && dropOut
            }
        }

        FormType.XIN_TIEP_TUC_HOC -> {
            combine(isValidateBaseInfo, isValidateContinueStudy) { baseInfo, dropOut ->
                baseInfo && dropOut
            }
        }

        FormType.CAP_LAI_THE_SINH_VIEN -> {
            isValidateBaseInfo
        }

        else -> {
            flowOf(false)
        }
    }

    private val isValidateDropOut = createFormModel.map {
        it.parentName.isNotEmpty() && it.parentPhone.isNotEmpty() &&
                it.parentAddress.isNotEmpty() && it.dropOffDate.isNotEmpty() &&
                it.dropOffReason.isNotEmpty() && it.personalId.isNotEmpty() && it.dateCCCD.isNotEmpty() && it.addressCCCD.isNotEmpty()
    }
    private val isValidateContinueStudy = createFormModel.map {
        it.pronouncementNumber.isNotEmpty() && it.signDate.isNotEmpty() &&
                it.reservedDate.isNotEmpty() && it.reservedMonth.isNotEmpty() &&
                it.continueStudyDate.isNotEmpty() && it.semester.isNotEmpty() && it.schoolYear.isNotEmpty()
                && it.personalId.isNotEmpty() && it.dateCCCD.isNotEmpty() && it.addressCCCD.isNotEmpty()
    }

    private val isValidateBHYT = createFormModel.map {
        it.nativeCountry.isNotEmpty() && it.bhytContent.isNotEmpty()
    }

    fun createForm(formTypeModel: FormTypeModel) {
        showLoading(true)
        val userId = SharePreferenceExt.userInfo.userId
        createFormUseCase.execute(
            studentId = userId,
            formId = formTypeModel.id,
            body = when (formTypeModel.type) {
                FormType.THOI_HOC -> createFormModel.value.toDropOutRequest()
                FormType.CAP_LAI_THE_BHYT -> createFormModel.value.toStudentHealthRequest()
                FormType.XIN_TIEP_TUC_HOC -> createFormModel.value.toContinueStudyRequest()
                FormType.CAP_LAI_THE_SINH_VIEN -> createFormModel.value.toBaseCreateFormRequest()
            }
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach {
            showLoading(false)
            setState(CreateFormContract.FormInfoState.CreateFormSuccess)
        }.launchIn(viewModelScopeExceptionHandler)
    }
}