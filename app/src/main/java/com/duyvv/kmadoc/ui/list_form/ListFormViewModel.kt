package com.duyvv.kmadoc.ui.list_form

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.GetListFormRequest
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.usecase.GetListFormsUseCase
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListFormViewModel @Inject constructor(
    private val getAllFormsUseCase: GetListFormsUseCase
) : BaseViewModel() {

    private val listFormRequest = GetListFormRequest()

    private val _listForm = MutableStateFlow<List<FormModel>>(emptyList())

    private val _keySearch = MutableStateFlow("")
    val keySearch = _keySearch.asStateFlow()

    val listForm = _listForm.combine(keySearch) { listForm, keySearch ->
        listForm.filter {
            it.student.username.contains(keySearch, ignoreCase = true)
                    || it.formType?.type?.titleVn?.contains(keySearch, ignoreCase = true) == true
                    || it.status.title.contains(keySearch, ignoreCase = true)
        }.sortedBy { it.createdAt }
    }

    fun updateKeySearch(key: String) {
        _keySearch.value = key
    }

    fun getListForms() {
        val isAdmin = SharePreferenceExt.isAdminAccount
        showLoading(true)
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