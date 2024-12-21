package com.duyvv.iddoc.ui.demoinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseNonBindingFragment
import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.data.model.FormModel
import com.duyvv.iddoc.data.model.FormStatus
import com.duyvv.iddoc.data.model.FormTypeModel
import com.duyvv.iddoc.ui.camera.cameraresult.CameraResultFragment.Companion.FORM_KEY
import com.duyvv.iddoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.iddoc.util.APPLICATION_DISTRICT_DATE
import com.duyvv.iddoc.util.CONTINUE_STUDY_DATE
import com.duyvv.iddoc.util.DROP_OFF_DATE
import com.duyvv.iddoc.util.RESERVED_DATE
import com.duyvv.iddoc.util.SIGNED_DATE
import com.duyvv.iddoc.util.STUDENT_BIRTH_DATE
import com.duyvv.iddoc.util.STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.fromJson
import com.duyvv.iddoc.util.openPickDate
import com.duyvv.iddoc.util.showConfirmDeleteDialog
import com.duyvv.iddoc.util.showSuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class DemoInfoFragment : BaseNonBindingFragment<DemoInfoViewModel>() {

    override val viewModel: DemoInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // loai don
        viewModel.typeModel = arguments?.getString(FORM_TYPE_KEY).fromJson<FormTypeModel>()
        viewModel.updateFormData(arguments?.getString(FORM_KEY).fromJson<FormModel>())
        val screenType = arguments?.getString(SCREEN_TYPE_KEY).fromJson<DemoScreenType>()
        return ComposeView(requireContext()).apply {
            setContent {
                DemoInfoScreen(
                    viewModel = viewModel,
                    screenType = screenType,
                    onClickSelectFormDate = {
                        openPickDate {
                            viewModel.updateData(APPLICATION_DISTRICT_DATE, it)
                        }
                    },
                    onClickSelectBirthday = {
                        openPickDate {
                            viewModel.updateData(STUDENT_BIRTH_DATE, it)
                        }
                    },
                    onClickSelectDateCCCD = {
                        openPickDate {
                            viewModel.updateData(STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE, it)
                        }
                    },
                    onClickSelectDropOutDate = {
                        openPickDate {
                            viewModel.updateData(DROP_OFF_DATE, it)
                        }
                    },
                    onSelectSignDate = {
                        openPickDate {
                            viewModel.updateData(SIGNED_DATE, it)
                        }
                    },
                    onClickSelectReservedDate = {
                        openPickDate {
                            viewModel.updateData(RESERVED_DATE, it)
                        }
                    },
                    onClickSelectContinueStudyDate = {
                        openPickDate {
                            viewModel.updateData(CONTINUE_STUDY_DATE, it)
                        }
                    },
                    onClickUploadForm = {
                        val adminId = SharePreferenceExt.userInfo.userId
                        viewModel.typeModel?.id?.let {
                            viewModel.uploadForm(
                                adminId = adminId,
                                categoryId = it
                            )
                        }
                    },
                    onClickDeleteForm = {
                        showConfirmDeleteDialog {
                            viewModel.deleteForm(
                                formId = viewModel.formModel.value.personalFormId
                            )
                        }
                    },
                    onClickUpdateForm = {
                        viewModel.updateForm(
                            formId = viewModel.formModel.value.personalFormId
                        )
                    },
                    onClickDenyForm = {
                        viewModel.updateStatusForm(
                            formId = viewModel.formModel.value.personalFormId,
                            status = FormStatus.DENIED.name
                        )
                    },
                    onClickApproveForm = {
                        viewModel.updateStatusForm(
                            formId = viewModel.formModel.value.personalFormId,
                            status = FormStatus.APPROVED.name
                        )
                    }
                )
            }
        }
    }

    override fun handleEffect(effect: MviEffect) {
        when (effect) {
            DemoInfoContract.DemoInfoEffect.UpdateStatusFormSuccess -> {
                showSuccessDialog("Cập nhật đơn thành công") {
                    findNavController().popBackStack()
                }
            }

            DemoInfoContract.DemoInfoEffect.UpdateFormSuccess -> {
                showSuccessDialog("Cập nhật đơn thành công") {
                    findNavController().popBackStack()
                }
            }

            DemoInfoContract.DemoInfoEffect.UploadFormSuccess -> {
                showSuccessDialog("Tạo đơn thành công") {
                    navigate(
                        id = R.id.homeFragment,
                        popUpTo = R.id.homeFragment,
                        popUpToBuilder = {
                            inclusive = true
                        }
                    )
                }
            }

            DemoInfoContract.DemoInfoEffect.DeleteFormSuccess -> {
                Toasty.success(requireContext(), "Xóa đơn thành công").show()
                findNavController().popBackStack()
            }
        }
    }

    companion object {
        const val SCREEN_TYPE_KEY = "SCREEN_TYPE_KEY"
    }
}