package com.duyvv.iddoc.ui.selectform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseNonBindingFragment
import com.duyvv.iddoc.data.model.FormTypeModel
import com.duyvv.iddoc.ui.home.MainActivity.Companion.IS_FROM_CAMERA
import com.duyvv.iddoc.util.toJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectFormTypeFragment : BaseNonBindingFragment<FormTypeViewModel>() {

    override val viewModel: FormTypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val isFromCamera = arguments?.getBoolean(IS_FROM_CAMERA) ?: false
        return ComposeView(requireContext()).apply {
            setContent {
                SelectFormTypeScreen(
                    viewModel = viewModel,
                    onClickItem = {
                        onClickItem(it, isFromCamera)
                    }
                )
            }
        }
    }

    private fun onClickItem(
        model: FormTypeModel,
        isFromCamera: Boolean
    ) {
        if (isFromCamera) {
            navigate(
                id = R.id.cameraFragment,
                extras = Bundle().apply {
                    putString(FORM_TYPE_KEY, model.toJson())
                }
            )
        } else {
            navigate(
                id = R.id.createFormFragment,
                extras = Bundle().apply {
                    putString(FORM_TYPE_KEY, model.toJson())
                }
            )
        }
    }

    companion object {
        const val FORM_TYPE_KEY = "formType"
    }
}