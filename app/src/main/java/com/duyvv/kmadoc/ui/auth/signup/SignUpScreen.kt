package com.duyvv.kmadoc.ui.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.ui.component.BaseButton
import com.duyvv.kmadoc.ui.component.BaseHeaderView
import com.duyvv.kmadoc.ui.component.PasswordTextField
import com.duyvv.kmadoc.ui.createform.forminfo.BaseInputTextFiled
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    onClickSignUp: () -> Unit,
    onClickConfigDomain: () -> Unit,
    onClickBack: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BaseHeaderView(
            modifier = modifier.align(Alignment.TopCenter),
            onClickBack = onClickBack
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BaseInputTextFiled(
                hint = "Mã sinh viên",
                value = viewModel.studentCode.collectAsStateWithLifecycle().value,
                onValueChange = {
                    viewModel.updateStudentCode(it)
                },
                singleLine = true,
                startIconRes = R.drawable.ic_user_id
            )
            Spacer(Modifier.height(20.dp))
            BaseInputTextFiled(
                hint = "Họ và tên",
                value = viewModel.userName.collectAsStateWithLifecycle().value,
                onValueChange = {
                    viewModel.updateUserName(it)
                },
                singleLine = true,
                startIconRes = R.drawable.ic_user_name
            )
            Spacer(Modifier.height(20.dp))
            PasswordTextField(
                hint = "Mật khẩu",
                value = viewModel.password.collectAsStateWithLifecycle().value,
                onValueChange = {
                    viewModel.updatePassword(it)
                },
                singleLine = true,
                keyboardType = KeyboardType.Password,
                startIconRes = R.drawable.ic_password_bold
            )
            Spacer(Modifier.height(20.dp))
            PasswordTextField(
                hint = "Xác nhận mật khẩu",
                value = viewModel.confirmPassword.collectAsStateWithLifecycle().value,
                onValueChange = {
                    viewModel.updateConfirmPassword(it)
                },
                singleLine = true,
                keyboardType = KeyboardType.Password,
                startIconRes = R.drawable.ic_re_password_bold
            )
            Spacer(Modifier.height(20.dp))
            BaseButton(
                modifier = modifier.height(65.dp),
                text = "Đăng ký",
                onClickButton = onClickSignUp,
                enabled = viewModel.isValidInput.collectAsStateWithLifecycle(false).value
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.nonAimClickable {
                    onClickConfigDomain.invoke()
                },
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(R.color.textSecondary),
                            fontSize = 14.sp
                        )
                    ) {
                        append("Config domain")
                    }
                }
            )
        }
    }
}