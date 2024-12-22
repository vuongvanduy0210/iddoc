package com.duyvv.kmadoc.ui.dialog.filterform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.duyvv.kmadoc.base.BaseNonBindingBottomSheetDialog
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.util.fromJson
import com.duyvv.kmadoc.util.toJson

class FilterFormDialog : BaseNonBindingBottomSheetDialog<FilterViewModel>() {

    override val viewModel: FilterViewModel by viewModels()

    var onApply: ((listSelectedType: List<FilterModel>, listSelectedStatus: List<FilterModel>) -> Unit)? =
        null

    override fun setWidth() = 1f

    override fun setHeight() = 1f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FilterFormScreen(
                    viewModel = viewModel,
                    onClickBack = {
                        dismiss()
                    },
                    onClickApply = {
                        val isSelectedAllType = viewModel.listType.value.all { it.isSelected }
                        val isSelectedAllStatus = viewModel.listStatus.value.all { it.isSelected }
                        val listType = if (isSelectedAllType) {
                            viewModel.listType.value.map { it.copy(isSelected = true) }
                        } else {
                            viewModel.listType.value
                        }
                        val listStatus = if (isSelectedAllStatus) {
                            viewModel.listStatus.value.map { it.copy(isSelected = true) }
                        } else {
                            viewModel.listStatus.value
                        }
                        onApply?.invoke(listType, listStatus)
                        dismiss()
                    }
                )
            }
        }
    }

    override fun initView() {
        val listType = arguments?.getString("listSelectedType")?.fromJson<List<FilterModel>>()
        val listStatus = arguments?.getString("listSelectedStatus")?.fromJson<List<FilterModel>>()
        if (listType != null) {
            viewModel.updateListType(listType)
        }
        if (listStatus != null) {
            viewModel.updateListStatus(listStatus)
        }
    }

    companion object {
        fun newInstance(
            listSelectedType: List<FilterModel>?,
            listSelectedStatus: List<FilterModel>?,
            onApply: ((listSelectedType: List<FilterModel>, listSelectedStatus: List<FilterModel>) -> Unit)? = null
        ): FilterFormDialog {
            return FilterFormDialog().apply {
                arguments = bundleOf(
                    "listSelectedType" to listSelectedType?.toJson(),
                    "listSelectedStatus" to listSelectedStatus?.toJson()
                )
                this.onApply = onApply
            }
        }
    }
}