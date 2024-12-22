package com.duyvv.kmadoc.ui.list_form

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.GetListFormRequest
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.usecase.GetListFormsUseCase
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListFormViewModel @Inject constructor(
    private val getAllFormsUseCase: GetListFormsUseCase
) : BaseViewModel() {

    private var listFormRequest = GetListFormRequest()

    private val _listForm = MutableStateFlow<List<FormModel>>(emptyList())

    private val _keySearch = MutableStateFlow("")
    val keySearch = _keySearch.asStateFlow()

    private val _startDate = MutableStateFlow("")
    val startDate = _startDate.asStateFlow()
    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()

    var listType: List<FilterModel>? = null
    var listStatus: List<FilterModel>? = null

    fun getSelectedStatus(): List<FilterModel>? {
        return listStatus?.filter { it.isSelected }
    }

    fun getSelectedType(): List<FilterModel>? {
        return listType?.filter { it.isSelected }
    }

    fun updateStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun updateEndDate(endDate: String) {
        _endDate.value = endDate
    }

    val listForm = _listForm.asStateFlow()

    fun updateKeySearch(key: String) {
        _keySearch.value = key
    }

    fun getListFormFilterTime() {
        listFormRequest = listFormRequest.copy(
            startDate = startDate.value,
            endDate = endDate.value
        )
        getListForms()
    }

    fun getListFormFilterType() {
        listFormRequest = listFormRequest.copy(
            types = listType?.filter { it.isSelected }?.map { it.id } ?: emptyList(),
            status = listStatus?.filter { it.isSelected }?.map { it.id } ?: emptyList()
        )
        getListForms()
    }

    fun getListFormWithKeySearch() {
        listFormRequest = listFormRequest.copy(
            keySearch = keySearch.value
        )
        getListForms(false)
    }

    fun getListForms(isShowLoading: Boolean = true) {
        val isAdmin = SharePreferenceExt.isAdminAccount
        showLoading(isShowLoading)
        getAllFormsUseCase.execute(
            studentId = if (isAdmin) null else SharePreferenceExt.userInfo.userId,
            categoryId = null,
            request = listFormRequest
        )
            .onEachError {
                showLoading(false)
                showError(it)
            }.onEach { response ->
                showLoading(false)
                val forms = response.info?.map { it.toDomainModel() } ?: emptyList()
                _listForm.value = forms
            }.launchIn(viewModelScopeExceptionHandler)
    }
}