package com.duyvv.kmadoc.ui.list_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseNonBindingFragment
import com.duyvv.kmadoc.ui.camera.cameraresult.CameraResultFragment.Companion.FORM_KEY
import com.duyvv.kmadoc.ui.demoinfo.DemoInfoFragment.Companion.SCREEN_TYPE_KEY
import com.duyvv.kmadoc.ui.demoinfo.DemoScreenType
import com.duyvv.kmadoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.kmadoc.util.toJson
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