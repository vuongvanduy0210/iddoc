package com.duyvv.kmadoc.ui.changeuserinfo.changepass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.base.BaseNonBindingFragment
import com.duyvv.kmadoc.base.mvi.MviEffect
import com.duyvv.kmadoc.ui.changeuserinfo.ChangeInfoContract
import com.duyvv.kmadoc.util.showSuccessDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseNonBindingFragment<ChangePasswordViewModel>() {

    override val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ChangePasswordScreen(
                    viewModel = viewModel,
                    onClickBack = {
                        findNavController().popBackStack()
                    },
                    onClickApply = {
                        viewModel.changePass()
                    }
                )
            }
        }
    }

    override fun handleEffect(effect: MviEffect) {
        when (effect) {
            ChangeInfoContract.ChangeInfoEffect.ChangePasswordSuccess -> {
                showSuccessDialog("Đổi mật khẩu thành công") {
                    findNavController().popBackStack()
                }
            }
        }
    }
}