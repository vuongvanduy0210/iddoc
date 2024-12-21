package com.duyvv.iddoc.ui.demoinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.iddoc.R
import com.duyvv.iddoc.data.model.FormModel
import com.duyvv.iddoc.data.model.FormStatus
import com.duyvv.iddoc.data.model.FormType
import com.duyvv.iddoc.ui.component.BaseButton
import com.duyvv.iddoc.ui.createform.forminfo.BaseInputTextFiled
import com.duyvv.iddoc.ui.createform.forminfo.GenderPicker
import com.duyvv.iddoc.util.APPLICATION_DISTRICT_DATE
import com.duyvv.iddoc.util.CONTENT
import com.duyvv.iddoc.util.CONTINUE_STUDY_DATE
import com.duyvv.iddoc.util.DROP_OFF_DATE
import com.duyvv.iddoc.util.DROP_OFF_REASON
import com.duyvv.iddoc.util.MAJOR
import com.duyvv.iddoc.util.NATIVE_COUNTRY
import com.duyvv.iddoc.util.PARENT_CURRENT_RESIDENT
import com.duyvv.iddoc.util.PARENT_NAME
import com.duyvv.iddoc.util.PARENT_PHONE_NUMBER
import com.duyvv.iddoc.util.PERMANENT_RESIDENT
import com.duyvv.iddoc.util.PRONOUNCEMENT_NUMBER
import com.duyvv.iddoc.util.RESERVED_DATE
import com.duyvv.iddoc.util.RESERVED_MONTH
import com.duyvv.iddoc.util.SCHOOL_YEAR
import com.duyvv.iddoc.util.SEMESTER
import com.duyvv.iddoc.util.SIGNED_DATE
import com.duyvv.iddoc.util.STUDENT_BIRTH_DATE
import com.duyvv.iddoc.util.STUDENT_CITIZEN_IDENTIFICATION
import com.duyvv.iddoc.util.STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION
import com.duyvv.iddoc.util.STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE
import com.duyvv.iddoc.util.STUDENT_CLASS
import com.duyvv.iddoc.util.STUDENT_GENDER
import com.duyvv.iddoc.util.STUDENT_ID
import com.duyvv.iddoc.util.STUDENT_NAME
import com.duyvv.iddoc.util.STUDENT_PHONE_NUMBER
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.getContentByFieldName

@Composable
fun DemoInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: DemoInfoViewModel,
    onClickSelectBirthday: () -> Unit = {},
    onClickSelectDateCCCD: () -> Unit = {},
    onClickSelectFormDate: () -> Unit,
    onClickSelectDropOutDate: () -> Unit,
    onSelectSignDate: () -> Unit,
    onClickSelectReservedDate: () -> Unit,
    onClickSelectContinueStudyDate: () -> Unit,
    onClickDeleteForm: () -> Unit,
    onClickUpdateForm: () -> Unit,
    onClickUploadForm: () -> Unit,
    onClickDenyForm: () -> Unit,
    onClickApproveForm: () -> Unit,
    screenType: DemoScreenType?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
            .padding(vertical = 20.dp, horizontal = 36.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val formModel by viewModel.formModel.collectAsStateWithLifecycle()
        val isAdmin by remember { mutableStateOf(SharePreferenceExt.isAdminAccount) }
        val isEditable by remember {
            mutableStateOf(
                screenType == DemoScreenType.TYPE_UPLOAD
                        || (formModel.status == FormStatus.STAGING && !isAdmin)
            )
        }
        Text(
            viewModel.typeModel?.type?.titleVn ?: "Thông tin đơn",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        BaseInputTextFiled(
            hint = "Địa chỉ và ngày làm đơn",
            value = formModel.getContentByFieldName(APPLICATION_DISTRICT_DATE),
            onValueChange = {
                viewModel.updateData(APPLICATION_DISTRICT_DATE, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Họ và tên",
            value = formModel.getContentByFieldName(STUDENT_NAME),
            onValueChange = {
                viewModel.updateData(STUDENT_NAME, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Số điện thoại",
            value = formModel.getContentByFieldName(STUDENT_PHONE_NUMBER),
            onValueChange = {
                viewModel.updateData(STUDENT_PHONE_NUMBER, it)
            },
            keyboardType = KeyboardType.Number,
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Ngành",
            value = formModel.getContentByFieldName(MAJOR),
            onValueChange = {
                viewModel.updateData(MAJOR, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Lớp",
            value = formModel.getContentByFieldName(STUDENT_CLASS),
            onValueChange = {
                viewModel.updateData(STUDENT_CLASS, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Mã sinh viên",
            value = formModel.getContentByFieldName(STUDENT_ID),
            onValueChange = {
                viewModel.updateData(STUDENT_ID, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Ngày sinh",
            value = formModel.getContentByFieldName(STUDENT_BIRTH_DATE),
            onValueChange = {},
            readOnly = !isEditable,
            endIconRes = R.drawable.ic_calendar_outlined,
            onClickEndIcon = onClickSelectBirthday,
        )
        GenderPicker(
            selectedGender = formModel.getContentByFieldName(STUDENT_GENDER),
            readOnly = !isEditable
        ) {
            viewModel.updateData(STUDENT_GENDER, it)
        }
        BaseInputTextFiled(
            hint = "Số CCCD",
            value = formModel.getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION),
            onValueChange = {
                viewModel.updateData(STUDENT_CITIZEN_IDENTIFICATION, it)
            },
            readOnly = !isEditable,
            keyboardType = KeyboardType.Number
        )
        BaseInputTextFiled(
            hint = "Ngày cấp CCCD",
            value = formModel.getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE),
            onValueChange = {},
            readOnly = !isEditable,
            endIconRes = R.drawable.ic_calendar_outlined,
            onClickEndIcon = onClickSelectDateCCCD
        )
        BaseInputTextFiled(
            hint = "Nơi cấp CCCD",
            value = formModel.getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION),
            onValueChange = {
                viewModel.updateData(STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION, it)
            },
            readOnly = !isEditable
        )
        BaseInputTextFiled(
            hint = "Hộ khẩu thường trú",
            value = formModel.getContentByFieldName(PERMANENT_RESIDENT),
            onValueChange = {
                viewModel.updateData(PERMANENT_RESIDENT, it)
            },
            readOnly = !isEditable
        )

        when (viewModel.typeModel?.type) {
            FormType.THOI_HOC -> {
                DropOutContent(
                    viewModel = viewModel,
                    onClickSelectDropOutDate = onClickSelectDropOutDate,
                    isEditable = isEditable
                )
            }

            FormType.CAP_LAI_THE_BHYT -> {
                StudentHealthContent(
                    viewModel = viewModel,
                    isEditable = isEditable
                )
            }

            FormType.XIN_TIEP_TUC_HOC -> {
                ContinueStudyContent(
                    viewModel = viewModel,
                    onClickSelectContinueStudyDate = onClickSelectContinueStudyDate,
                    onClickSelectReservedDate = onClickSelectReservedDate,
                    onSelectSignDate = onSelectSignDate,
                    isEditable = isEditable
                )
            }

            else -> {}
        }

        ButtonView(
            modifier = modifier,
            isEditable = isEditable,
            isAdmin = isAdmin,
            onClickDeleteForm = onClickDeleteForm,
            onClickUpdateForm = onClickUpdateForm,
            onClickUploadForm = onClickUploadForm,
            onClickDenyForm = onClickDenyForm,
            onClickApproveForm = onClickApproveForm,
            screenType = screenType,
            formModel = formModel
        )
    }
}

@Composable
fun DropOutContent(
    modifier: Modifier = Modifier,
    viewModel: DemoInfoViewModel,
    onClickSelectDropOutDate: () -> Unit,
    isEditable: Boolean = false
) {
    val formModel by viewModel.formModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Họ và tên bố/mẹ",
        value = formModel.getContentByFieldName(PARENT_NAME),
        onValueChange = {
            viewModel.updateData(PARENT_NAME, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Số điện thoại bố/mẹ",
        value = formModel.getContentByFieldName(PARENT_PHONE_NUMBER),
        onValueChange = {
            viewModel.updateData(PARENT_PHONE_NUMBER, it)
        },
        readOnly = !isEditable,
        keyboardType = KeyboardType.Number
    )
    BaseInputTextFiled(
        hint = "Chỗ ở hiện tại của bố/mẹ",
        value = formModel.getContentByFieldName(PARENT_CURRENT_RESIDENT),
        onValueChange = {
            viewModel.updateData(PARENT_CURRENT_RESIDENT, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Ngày thôi học",
        value = formModel.getContentByFieldName(DROP_OFF_DATE),
        readOnly = !isEditable,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectDropOutDate
    )
    BaseInputTextFiled(
        hint = "Lý do thôi học",
        value = formModel.getContentByFieldName(DROP_OFF_REASON),
        onValueChange = {
            viewModel.updateData(DROP_OFF_REASON, it)
        },
        readOnly = !isEditable
    )
}

@Composable
fun ContinueStudyContent(
    modifier: Modifier = Modifier,
    viewModel: DemoInfoViewModel,
    onSelectSignDate: () -> Unit,
    onClickSelectReservedDate: () -> Unit,
    onClickSelectContinueStudyDate: () -> Unit,
    isEditable: Boolean = false
) {
    val formModel by viewModel.formModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Quyết định số",
        value = formModel.getContentByFieldName(PRONOUNCEMENT_NUMBER),
        onValueChange = {
            viewModel.updateData(PRONOUNCEMENT_NUMBER, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Ngày ký quyết định",
        value = formModel.getContentByFieldName(SIGNED_DATE),
        readOnly = !isEditable,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onSelectSignDate
    )
    BaseInputTextFiled(
        hint = "Ngày bảo lưu kết quả",
        value = formModel.getContentByFieldName(RESERVED_DATE),
        readOnly = !isEditable,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectReservedDate
    )
    BaseInputTextFiled(
        hint = "Số tháng bảo lưu",
        value = formModel.getContentByFieldName(RESERVED_MONTH),
        keyboardType = KeyboardType.Number,
        onValueChange = {
            viewModel.updateData(RESERVED_MONTH, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Ngày tiếp tục học tập",
        value = formModel.getContentByFieldName(CONTINUE_STUDY_DATE),
        readOnly = !isEditable,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectContinueStudyDate
    )
    BaseInputTextFiled(
        hint = "Học kì",
        keyboardType = KeyboardType.Number,
        value = formModel.getContentByFieldName(SEMESTER),
        onValueChange = {
            viewModel.updateData(SEMESTER, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Năm học",
        value = formModel.getContentByFieldName(SCHOOL_YEAR),
        onValueChange = {
            viewModel.updateData(SCHOOL_YEAR, it)
        },
        readOnly = !isEditable
    )
}

@Composable
fun StudentHealthContent(
    modifier: Modifier = Modifier,
    viewModel: DemoInfoViewModel,
    isEditable: Boolean = false
) {
    val formModel by viewModel.formModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Quê quán",
        value = formModel.getContentByFieldName(NATIVE_COUNTRY),
        onValueChange = {
            viewModel.updateData(NATIVE_COUNTRY, it)
        },
        readOnly = !isEditable
    )
    BaseInputTextFiled(
        hint = "Nội dung",
        value = formModel.getContentByFieldName(CONTENT),
        onValueChange = {
            viewModel.updateData(CONTENT, it)
        },
        readOnly = !isEditable
    )
}

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    isEditable: Boolean = false,
    isAdmin: Boolean = false,
    formModel: FormModel,
    onClickDeleteForm: () -> Unit,
    onClickUpdateForm: () -> Unit,
    onClickUploadForm: () -> Unit,
    onClickDenyForm: () -> Unit,
    onClickApproveForm: () -> Unit,
    screenType: DemoScreenType?
) {
    if (screenType == DemoScreenType.TYPE_UPLOAD) {
        BaseButton(
            text = "Đăng đơn",
            onClickButton = onClickUploadForm,
            modifier = modifier.fillMaxWidth(),
            enabled = true,
            containerColor = colorResource(R.color.color_enabled)
        )
    } else if (screenType == DemoScreenType.TYPE_EDIT && isAdmin) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            BaseButton(
                text = "Từ chối",
                onClickButton = onClickDenyForm,
                modifier = modifier.weight(1f),
                containerColor = Color.Red,
                enabled = formModel.status == FormStatus.STAGING
            )
            BaseButton(
                text = "Duyệt đơn",
                onClickButton = onClickApproveForm,
                modifier = modifier.weight(1f),
                containerColor = colorResource(R.color.colorApproved),
                enabled = formModel.status == FormStatus.STAGING
            )
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            BaseButton(
                text = "Xoá đơn",
                onClickButton = onClickDeleteForm,
                modifier = modifier.weight(1f),
                enabled = isEditable,
                containerColor = Color.Red
            )
            BaseButton(
                text = "Cập nhật đơn",
                onClickButton = onClickUpdateForm,
                modifier = modifier.weight(1f),
                enabled = isEditable
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Pre1(modifier: Modifier = Modifier) {
    BaseInputTextFiled(
        onValueChange = {},
        hint = "Họ và tên"
    )
}

@Composable
@Preview(showBackground = true)
fun Pre2(modifier: Modifier = Modifier) {
    GenderPicker {

    }
}