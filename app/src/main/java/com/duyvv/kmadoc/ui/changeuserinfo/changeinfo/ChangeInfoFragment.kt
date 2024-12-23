package com.duyvv.kmadoc.ui.changeuserinfo.changeinfo

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
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.showSuccessDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeInfoFragment : BaseNonBindingFragment<ChangeInfoViewModel>() {

    override val viewModel: ChangeInfoViewModel by viewModels()

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
                ChangeInfoScreen(
                    viewModel = viewModel,
                    onClickBack = {
                        findNavController().popBackStack()
                    },
                    onClickUpdateInfo = {
                        if (SharePreferenceExt.isAdminAccount)
                            viewModel.updateAdminInfo(
                                SharePreferenceExt.userInfo.userId
                            )
                        else {
                            viewModel.updateStudentInfo(
                                SharePreferenceExt.userInfo.userId
                            )
                        }
                    }
                )
            }
        }
    }

    override fun initData() {
        if (SharePreferenceExt.isAdminAccount) {
            viewModel.getAdminInfo()
        } else {
            viewModel.getStudentInfo()
        }
    }

    override fun handleEffect(effect: MviEffect) {
        when (effect) {
            is ChangeInfoContract.ChangeInfoEffect.ChangeInfoSuccess -> {
                showSuccessDialog(title = "Cập nhật thông tin thành công") {
                    findNavController().popBackStack()
                }
            }

            is ChangeInfoContract.ChangeInfoEffect.GetStudentInfoSuccess -> {

            }

            is ChangeInfoContract.ChangeInfoEffect.GetAdminInfoSuccess -> {

            }
        }
    }
}