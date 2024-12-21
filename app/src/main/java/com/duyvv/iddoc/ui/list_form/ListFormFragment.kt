package com.duyvv.iddoc.ui.list_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseNonBindingFragment
import com.duyvv.iddoc.ui.camera.cameraresult.CameraResultFragment.Companion.FORM_KEY
import com.duyvv.iddoc.ui.demoinfo.DemoInfoFragment.Companion.SCREEN_TYPE_KEY
import com.duyvv.iddoc.ui.demoinfo.DemoScreenType
import com.duyvv.iddoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.iddoc.util.toJson
import dagger.hilt.android.AndroidEntryPoint

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
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListForms()
    }
}