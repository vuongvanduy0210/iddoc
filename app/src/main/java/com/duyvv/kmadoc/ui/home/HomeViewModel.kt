package com.duyvv.kmadoc.ui.home

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.usecase.CountFormsUseCase
import com.duyvv.kmadoc.data.usecase.GetListFormsUseCase
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFormsUseCase: GetListFormsUseCase,
    private val countFormsUseCase: CountFormsUseCase
) : BaseViewModel() {

    /*private val _listForm = MutableStateFlow<List<FormModel>>(emptyList())
    val listForm = _listForm.asStateFlow()*/

    private val _countForms = MutableStateFlow<Int>(0)
    val countForms = _countForms.asStateFlow()

    init {
        getListForm()
    }

    private fun getListForm() {
        val isAdmin = SharePreferenceExt.isAdminAccount
        countFormsUseCase.execute()
            .onEachError {
                showLoading(false)
//                showError(it)
            }.onEach { response ->
                showLoading(false)
                _countForms.value = response.data?.totalForms ?: 0
            }.launchIn(viewModelScopeExceptionHandler)
    }
}