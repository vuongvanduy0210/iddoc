package com.duyvv.kmadoc.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.ui.component.BaseButton
import com.duyvv.kmadoc.ui.component.PasswordTextField
import com.duyvv.kmadoc.ui.createform.forminfo.BaseInputTextFiled
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onClickLogin: () -> Unit,
    onClickSignUp: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_log_in),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
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
                hint = "Tài khoản",
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
                startIconRes = R.drawable.ic_password
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(interactionSource = null, indication = null) {
                        viewModel.updateIsAdminLogin(!viewModel.isAdminLogin.value)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.isAdminLogin.collectAsStateWithLifecycle().value,
                    onCheckedChange = {
                        viewModel.updateIsAdminLogin(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.primary_color)
                    )
                )
                Text("Đăng nhập với tư cách admin")
            }
            BaseButton(
                modifier = modifier.height(65.dp),
                text = "Đăng nhập",
                onClickButton = onClickLogin,
                enabled = viewModel.isValidateInput.collectAsStateWithLifecycle(false).value
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.nonAimClickable {
                    onClickSignUp.invoke()
                },
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(R.color.textSecondary),
                            fontSize = 14.sp
                        )
                    ) {
                        append("Bạn chưa có tài khoản? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(R.color.primary_color),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Đăng ký")
                    }
                }
            )
        }
    }
}