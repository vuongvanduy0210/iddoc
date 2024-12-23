package com.duyvv.kmadoc.ui.changeuserinfo.changepass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.ui.component.BaseButton
import com.duyvv.kmadoc.ui.component.BaseHeaderView
import com.duyvv.kmadoc.ui.component.PasswordTextField

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangePasswordViewModel,
    onClickBack: () -> Unit,
    onClickApply: () -> Unit
) {
    Box {
        BaseHeaderView(
            modifier = modifier.align(Alignment.TopCenter),
            "Đổi mật khẩu",
            onClickBack = {
                onClickBack.invoke()
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 36.dp)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PasswordTextField(
            hint = "Mật khẩu cũ",
            value = viewModel.oldPassword.collectAsStateWithLifecycle().value,
            onValueChange = {
                viewModel.updateOldPassword(it)
            },
            singleLine = true,
            keyboardType = KeyboardType.Password,
            startIconRes = R.drawable.ic_password_bold
        )
        Spacer(Modifier.height(20.dp))
        PasswordTextField(
            hint = "Mật khẩu mới",
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
            text = "Cập nhật mật khẩu",
            onClickButton = onClickApply,
            enabled = viewModel.isValidInput.collectAsStateWithLifecycle(false).value
        )
    }
}