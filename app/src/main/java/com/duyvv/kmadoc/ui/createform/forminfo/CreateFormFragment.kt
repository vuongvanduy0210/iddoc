package com.duyvv.kmadoc.ui.createform.forminfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseNonBindingFragment
import com.duyvv.kmadoc.base.mvi.MviState
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.kmadoc.util.fromJson
import com.duyvv.kmadoc.util.openPickDate
import com.duyvv.kmadoc.util.showSuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class CreateFormFragment : BaseNonBindingFragment<CreateFormViewModel>() {

    override val viewModel: CreateFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // loai don
        val formTypeModel = arguments?.getString(FORM_TYPE_KEY).fromJson<FormTypeModel>()
        // loai tuong tac voi don: tao, upload, cap nhat
        return ComposeView(requireContext()).apply {
            setContent {
                CreateFormScreen(
                    viewModel = viewModel,
                    formTypeModel = formTypeModel,
                    onClickSelectBirthday = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(birthday = result)
                            }
                        }
                    },
                    onClickSelectPersonalIdDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(dateCCCD = result)
                            }
                        }
                    },
                    onClickSelectFormDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(formDate = result)
                            }
                        }
                    },
                    onClickContinue = {
                        viewModel.createForm(formTypeModel!!)
                    },
                    onClickSelectDropOutDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(dropOffDate = result)
                            }
                        }
                    },
                    onSelectSignDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(signDate = result)
                            }
                        }
                    },
                    onClickSelectReservedDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(reservedDate = result)
                            }
                        }
                    },
                    onClickSelectContinueStudyDate = {
                        openPickDate { result ->
                            viewModel.createFormModel.update {
                                it.copy(continueStudyDate = result)
                            }
                        }
                    }
                )
            }
        }
    }

    override fun handleState(state: MviState) {
        when (state) {
            is CreateFormContract.FormInfoState.CreateFormSuccess -> {
                showSuccessDialog("Tạo đơn thành công, vui lòng chờ giáo viên xét duyệt") {
                    navigate(
                        id = R.id.homeFragment,
                        popUpTo = R.id.homeFragment,
                        popUpToBuilder = {
                            inclusive = true
                        }
                    )
                }
            }
        }
    }
}