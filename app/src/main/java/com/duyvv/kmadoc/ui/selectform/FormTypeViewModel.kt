package com.duyvv.kmadoc.ui.selectform

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.data.usecase.GetFormTypesUseCase
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toFormTypeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FormTypeViewModel @Inject constructor(
    private val getFormTypesUseCase: GetFormTypesUseCase
) : BaseViewModel() {

    private val _listForm = MutableStateFlow<List<FormTypeModel>>(emptyList())
    val listForm = _listForm.asStateFlow()

    init {
        getAllFormTypes()
    }

    private fun getAllFormTypes() {
        showLoading(true)
        getFormTypesUseCase.execute(null).onEachError {
            showLoading(false)
            showError(it)
        }.onEach { response ->
            showLoading(false)
            response.data?.map { it.toFormTypeModel() }?.let {
                _listForm.value = it
            }
        }.launchIn(viewModelScopeExceptionHandler)
    }
}