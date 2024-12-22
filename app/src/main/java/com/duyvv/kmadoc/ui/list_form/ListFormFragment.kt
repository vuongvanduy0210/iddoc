package com.duyvv.kmadoc.ui.list_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseNonBindingFragment
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.data.model.FormStatus
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.ui.camera.cameraresult.CameraResultFragment.Companion.FORM_KEY
import com.duyvv.kmadoc.ui.demoinfo.DemoInfoFragment.Companion.SCREEN_TYPE_KEY
import com.duyvv.kmadoc.ui.demoinfo.DemoScreenType
import com.duyvv.kmadoc.ui.dialog.FilterTimeDialog
import com.duyvv.kmadoc.ui.dialog.filterform.FilterFormDialog
import com.duyvv.kmadoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.kmadoc.util.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest

@AndroidEntryPoint
class ListFormFragment : BaseNonBindingFragment<ListFormViewModel>() {
    override val viewModel: ListFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ListFormScreen(
                    viewModel = viewModel,
                    onClickBack = {
                        findNavController().navigateUp()
                    },
                    onClickItem = {
                        navigate(
                            id = R.id.demoInfoFragment,
                            extras = bundleOf(
                                FORM_TYPE_KEY to it.formType.toJson(),
                                FORM_KEY to it.toJson(),
                                SCREEN_TYPE_KEY to DemoScreenType.TYPE_EDIT.toJson()
                            )
                        )
                    },
                    onClickFilterTime = {
                        FilterTimeDialog.newInstance(
                            startDate = viewModel.startDate.value,
                            endDate = viewModel.endDate.value,
                            onClickCancelFilter = {
                                viewModel.updateStartDate("")
                                viewModel.updateEndDate("")
                                viewModel.getListFormFilterTime()
                            },
                            onApply = { startDate, endDate ->
                                viewModel.updateStartDate(startDate)
                                viewModel.updateEndDate(endDate)
                                viewModel.getListFormFilterTime()
                            }
                        ).show(parentFragmentManager, "")
                    },
                    onClickFilterType = {
                        FilterFormDialog.newInstance(
                            listSelectedType = viewModel.listType,
                            listSelectedStatus = viewModel.listStatus,
                        ) { listSelectedType, listSelectedStatus ->
                            viewModel.listType = listSelectedType
                            viewModel.listStatus = listSelectedStatus
                            viewModel.getListFormFilterType()
                        }.show(parentFragmentManager, "")
                    }
                )
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun addObserver() {
        viewModel.keySearch.debounce(1000).mapLatest {
            viewModel.getListFormWithKeySearch()
        }.launchIn(lifecycleScope)
    }

    override fun initView() {
        viewModel.listType = FormType.entries.map { FilterModel(it.id, it.titleVn, true) }
        viewModel.listStatus = FormStatus.entries.map { FilterModel(it.name, it.title, true) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListForms()
    }
}