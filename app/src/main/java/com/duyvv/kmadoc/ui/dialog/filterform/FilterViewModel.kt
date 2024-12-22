package com.duyvv.kmadoc.ui.dialog.filterform

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.model.FilterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

) : BaseViewModel() {

    private val _listStatus = MutableStateFlow<List<FilterModel>>(emptyList())
    val listStatus = _listStatus.asStateFlow()

    private val _listType = MutableStateFlow<List<FilterModel>>(emptyList())
    val listType = _listType.asStateFlow()

    val isSelectAllType = _listType.map { it ->
        it.all { it.isSelected }
    }

    val isSelectAllStatus = _listStatus.map { it ->
        it.all { it.isSelected }
    }

    fun updateListStatus(list: List<FilterModel>) {
        _listStatus.value = list
    }

    fun updateListType(list: List<FilterModel>) {
        _listType.value = list
    }

    fun onClickSelectedAllType(isSelected: Boolean) {
        val list = listType.value.toMutableList().map { it.copy(isSelected = isSelected) }
        _listType.value = list
    }

    fun onClickSelectedAllStatus(isSelected: Boolean) {
        val updatedList = listStatus.value.map { it.copy(isSelected = isSelected) }
        _listStatus.value = updatedList
    }

    fun onClickItemFilterType(index: Int, item: FilterModel) {
        val list = listType.value.toMutableList()
        list[index] = list[index].copy(isSelected = !item.isSelected)
        _listType.value = list
    }

    fun onClickItemFilterStatus(index: Int, item: FilterModel) {
        val list = listStatus.value.toMutableList()
        list[index] = list[index].copy(isSelected = !item.isSelected)
        _listStatus.value = list
    }
}