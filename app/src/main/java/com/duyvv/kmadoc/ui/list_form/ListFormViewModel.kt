package com.duyvv.kmadoc.ui.list_form

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.base.mvi.MviIntent
import com.duyvv.kmadoc.data.dto.request.GetListFormRequest
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.usecase.GetListFormsUseCase
import com.duyvv.kmadoc.util.PAGE_SIZE
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListFormViewModel @Inject constructor(
    private val getAllFormsUseCase: GetListFormsUseCase
) : BaseViewModel() {

    private var listFormRequest = GetListFormRequest(limit = PAGE_SIZE, currentPage = 1)

    private val _listForm = MutableStateFlow<List<FormModel>>(emptyList())

    private val _keySearch = MutableStateFlow("")
    val keySearch = _keySearch.asStateFlow()

    private val _startDate = MutableStateFlow("")
    val startDate = _startDate.asStateFlow()
    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()

    var endReached = false

    var listType: List<FilterModel>? = null
    var listStatus: List<FilterModel>? = null

    val listForm = _listForm.asStateFlow()

    private fun loadMore(isShowLoading: Boolean = true, isRefresh: Boolean = false) {
        listFormRequest = if (isRefresh) {
            listFormRequest.copy(currentPage = 1)
        } else {
            listFormRequest.copy(currentPage = listFormRequest.currentPage + 1)
        }
        getListForms(isShowLoading = isShowLoading, isRefresh = isRefresh)
    }

    private fun getListFormFilterTime() {
        listFormRequest = listFormRequest.copy(
            currentPage = 1,
            startDate = startDate.value,
            endDate = endDate.value
        )
        getListForms(isRefresh = true)
    }

    private fun getListFormFilterType() {
        listFormRequest = listFormRequest.copy(
            currentPage = 1,
            types = listType?.filter { it.isSelected }?.map { it.id } ?: emptyList(),
            status = listStatus?.filter { it.isSelected }?.map { it.id } ?: emptyList()
        )
        getListForms(isRefresh = true)
    }

    fun getListFormWithKeySearch() {
        listFormRequest = listFormRequest.copy(
            currentPage = 1,
            keySearch = keySearch.value
        )
        getListForms(isShowLoading = false, isRefresh = true)
    }

    private fun getListForms(isShowLoading: Boolean = true, isRefresh: Boolean) {
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
                if (isRefresh) {
                    setState(ListFormContract.ListFormState.RefreshItemLoaded)
                    _listForm.value = forms
                } else {
                    _listForm.update { it + forms }
                    setState(ListFormContract.ListFormState.LoadMoreItemLoaded)
                }
                endReached = forms.size < PAGE_SIZE
            }.launchIn(viewModelScopeExceptionHandler)
    }

    override fun handleIntent(intent: MviIntent) {
        when (intent) {
            is ListFormContract.ListFormIntent.FilterTime -> {
                endReached = false
                _startDate.value = intent.startDate
                _endDate.value = intent.endDate
                getListFormFilterTime()
            }

            is ListFormContract.ListFormIntent.FilterType -> {
                endReached = false
                listType = intent.listType
                listStatus = intent.listStatus
                getListFormFilterType()
            }

            ListFormContract.ListFormIntent.FirstLoadItem -> {
                endReached = false
                loadMore(isShowLoading = false, isRefresh = true)
            }

            ListFormContract.ListFormIntent.LoadMoreItem -> {
                setState(ListFormContract.ListFormState.LoadMoreItemLoading)
                loadMore(isShowLoading = false, isRefresh = false)
            }

            ListFormContract.ListFormIntent.RefreshItem -> {
                endReached = false
                setState(ListFormContract.ListFormState.RefreshItemLoading)
                loadMore(isShowLoading = false, isRefresh = true)
            }

            is ListFormContract.ListFormIntent.SearchItem -> {
                endReached = false
                _keySearch.value = intent.keySearch
            }
        }
    }
}