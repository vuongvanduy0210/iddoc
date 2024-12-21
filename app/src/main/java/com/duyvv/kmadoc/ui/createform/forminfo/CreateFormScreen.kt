package com.duyvv.kmadoc.ui.createform.forminfo

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.ui.component.BaseButton
import kotlinx.coroutines.flow.update

@Composable
fun CreateFormScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateFormViewModel,
    formTypeModel: FormTypeModel?,
    onClickSelectBirthday: () -> Unit,
    onClickSelectPersonalIdDate: () -> Unit,
    onClickSelectFormDate: () -> Unit,
    onClickContinue: () -> Unit = {},
    onClickSelectDropOutDate: () -> Unit,
    onSelectSignDate: () -> Unit,
    onClickSelectReservedDate: () -> Unit,
    onClickSelectContinueStudyDate: () -> Unit
) {
    val createFormModel by viewModel.createFormModel.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
            .padding(vertical = 20.dp, horizontal = 36.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            formTypeModel?.type?.titleVn ?: "Nhập thông tin đơn",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        BaseInputTextFiled(
            hint = "Địa chỉ và ngày làm đơn",
            value = createFormModel.formDate,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(formDate = result)
                }
            }
        )
        BaseInputTextFiled(
            hint = "Họ và tên",
            value = createFormModel.fullName,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(fullName = result)
                }
            }
        )
        BaseInputTextFiled(
            hint = "Số điện thoại",
            value = createFormModel.phoneNumber,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(phoneNumber = result)
                }
            },
            keyboardType = KeyboardType.Number
        )
        BaseInputTextFiled(
            hint = "Ngành",
            value = createFormModel.major,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(major = result)
                }
            }
        )
        BaseInputTextFiled(
            hint = "Lớp",
            value = createFormModel.mClass,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(mClass = result)
                }
            }
        )
        BaseInputTextFiled(
            hint = "Mã sinh viên",
            value = createFormModel.studentId,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(studentId = result)
                }
            }
        )
        BaseInputTextFiled(
            value = createFormModel.birthday,
            onValueChange = {},
            hint = "Ngày sinh",
            endIconRes = R.drawable.ic_calendar_outlined,
            onClickEndIcon = onClickSelectBirthday
        )
        GenderPicker(
            selectedGender = createFormModel.gender
        ) { result ->
            viewModel.createFormModel.update {
                it.copy(gender = result)
            }
        }
        BaseInputTextFiled(
            hint = "Số CCCD",
            value = createFormModel.personalId,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(personalId = result)
                }
            },
            keyboardType = KeyboardType.Number
        )
        BaseInputTextFiled(
            hint = "Ngày cấp CCCD",
            value = createFormModel.dateCCCD,
            onValueChange = {},
            endIconRes = R.drawable.ic_calendar_outlined,
            onClickEndIcon = onClickSelectPersonalIdDate
        )
        BaseInputTextFiled(
            hint = "Nơi cấp CCCD",
            value = createFormModel.addressCCCD,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(addressCCCD = result)
                }
            },
            singleLine = false
        )
        BaseInputTextFiled(
            hint = "Hộ khẩu thường trú",
            value = createFormModel.address,
            onValueChange = { result ->
                viewModel.createFormModel.update {
                    it.copy(address = result)
                }
            },
            singleLine = false
        )
        when (formTypeModel?.type) {
            FormType.THOI_HOC -> {
                DropOutContent(
                    viewModel = viewModel,
                    onClickSelectDropOutDate = onClickSelectDropOutDate
                )
            }

            FormType.CAP_LAI_THE_BHYT -> {
                StudentHealthContent(
                    viewModel = viewModel
                )
            }

            FormType.XIN_TIEP_TUC_HOC -> {
                ContinueStudyContent(
                    viewModel = viewModel,
                    onClickSelectContinueStudyDate = onClickSelectContinueStudyDate,
                    onClickSelectReservedDate = onClickSelectReservedDate,
                    onSelectSignDate = onSelectSignDate
                )
            }

            else -> {}
        }
        BaseButton(
            text = "Tạo đơn",
            onClickButton = { onClickContinue.invoke() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            enabled = viewModel.isValidateInput(formTypeModel?.type)
                .collectAsStateWithLifecycle(false).value,
        )
    }
}

@Composable
fun DropOutContent(
    modifier: Modifier = Modifier,
    viewModel: CreateFormViewModel,
    onClickSelectDropOutDate: () -> Unit
) {
    val createFormModel by viewModel.createFormModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Họ và tên bố/mẹ",
        value = createFormModel.parentName,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(parentName = result)
            }
        }
    )
    BaseInputTextFiled(
        hint = "Số điện thoại bố/mẹ",
        value = createFormModel.parentPhone,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(parentPhone = result)
            }
        },
        keyboardType = KeyboardType.Number
    )
    BaseInputTextFiled(
        hint = "Chỗ ở hiện tại của bố/mẹ",
        value = createFormModel.parentAddress,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(parentAddress = result)
            }
        },
        singleLine = false
    )
    BaseInputTextFiled(
        hint = "Ngày thôi học",
        value = createFormModel.dropOffDate,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectDropOutDate
    )
    BaseInputTextFiled(
        hint = "Lý do thôi học",
        value = createFormModel.dropOffReason,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(dropOffReason = result)
            }
        },
        singleLine = false
    )
}

@Composable
fun ContinueStudyContent(
    modifier: Modifier = Modifier,
    viewModel: CreateFormViewModel,
    onSelectSignDate: () -> Unit,
    onClickSelectReservedDate: () -> Unit,
    onClickSelectContinueStudyDate: () -> Unit,
) {
    val createFormModel by viewModel.createFormModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Quyết định số",
        value = createFormModel.pronouncementNumber,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(pronouncementNumber = result)
            }
        },
    )
    BaseInputTextFiled(
        hint = "Ngày ký quyết định",
        value = createFormModel.signDate,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onSelectSignDate
    )
    BaseInputTextFiled(
        hint = "Ngày bảo lưu kết quả",
        value = createFormModel.reservedDate,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectReservedDate
    )
    BaseInputTextFiled(
        hint = "Số tháng bảo lưu",
        value = createFormModel.reservedMonth,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(reservedMonth = result)
            }
        },
        keyboardType = KeyboardType.Number,
    )
    BaseInputTextFiled(
        hint = "Ngày tiếp tục học tập",
        value = createFormModel.continueStudyDate,
        endIconRes = R.drawable.ic_calendar_outlined,
        onClickEndIcon = onClickSelectContinueStudyDate
    )
    BaseInputTextFiled(
        hint = "Học kì",
        keyboardType = KeyboardType.Number,
        value = createFormModel.semester,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(semester = result)
            }
        },
    )
    BaseInputTextFiled(
        hint = "Năm học",
        value = createFormModel.schoolYear,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(schoolYear = result)
            }
        },
    )
}

@Composable
fun StudentHealthContent(
    modifier: Modifier = Modifier,
    viewModel: CreateFormViewModel
) {
    val createFormModel by viewModel.createFormModel.collectAsStateWithLifecycle()
    BaseInputTextFiled(
        hint = "Quê quán",
        value = createFormModel.nativeCountry,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(nativeCountry = result)
            }
        },
        singleLine = false
    )
    BaseInputTextFiled(
        hint = "Nội dung",
        value = createFormModel.bhytContent,
        onValueChange = { result ->
            viewModel.createFormModel.update {
                it.copy(bhytContent = result)
            }
        },
        singleLine = false
    )
}

@Composable
fun BaseInputTextFiled(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    onClickEndIcon: () -> Unit = {},
    onClickStartIcon: () -> Unit = {},
    endIconRes: Int? = null,
    startIconRes: Int? = null,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .focusable(!readOnly)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClickEndIcon.invoke() })
            },
        shape = RoundedCornerShape(16.dp),
        singleLine = singleLine,
        label = {
            Text(hint, color = Color(0xFF787878), fontSize = 15.sp)
        },
        textStyle = TextStyle(fontSize = 15.sp),
        readOnly = readOnly,
        leadingIcon = if (startIconRes == null) null else {
            {
                IconButton(
                    onClick = { if (!readOnly) onClickStartIcon.invoke() }
                ) {
                    Icon(
                        painter = painterResource(id = startIconRes),
                        contentDescription = null
                    )
                }
            }
        },
        trailingIcon = if (endIconRes == null) null else {
            {
                IconButton(
                    onClick = { if (!readOnly) onClickEndIcon.invoke() }
                ) {
                    Icon(
                        painter = painterResource(id = endIconRes),
                        contentDescription = null
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        )
    )
}

@Composable
fun GenderPicker(
    selectedGender: String = "",
    readOnly: Boolean = false,
    onGenderSelected: (String) -> Unit
) {
    val genders = listOf("Nam", "Nữ")
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        genders.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = selectedGender == gender,
                        onClick = { if (!readOnly) onGenderSelected(gender) }
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedGender == gender,
                    onClick = { if (!readOnly) onGenderSelected(gender) }
                )
                Text(text = gender)
            }
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