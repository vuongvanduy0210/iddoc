package com.duyvv.iddoc.ui.home

import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.data.dto.request.GetListFormRequest
import com.duyvv.iddoc.data.model.FormModel
import com.duyvv.iddoc.data.usecase.GetListFormsUseCase
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.onEachError
import com.duyvv.iddoc.util.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFormsUseCase: GetListFormsUseCase
) : BaseViewModel() {

    private val _listForm = MutableStateFlow<List<FormModel>>(emptyList())
    val listForm = _listForm.asStateFlow()

    init {
        getListForm()
    }

    private fun getListForm() {
        val isAdmin = SharePreferenceExt.isAdminAccount
        getAllFormsUseCase.execute(
            studentId = if (isAdmin) null else SharePreferenceExt.userInfo.userId,
            categoryId = null,
            request = GetListFormRequest()
        )
            .onEachError {
                showLoading(false)
//                showError(it)
            }.onEach { response ->
                showLoading(false)
                _listForm.value = response.info?.map { it.toDomainModel() } ?: emptyList()
            }.launchIn(viewModelScopeExceptionHandler)
    }
}