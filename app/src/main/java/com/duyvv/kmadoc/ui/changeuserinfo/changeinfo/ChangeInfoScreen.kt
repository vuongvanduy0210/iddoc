package com.duyvv.kmadoc.ui.changeuserinfo.changeinfo

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.ui.component.BaseButton
import com.duyvv.kmadoc.ui.component.BaseHeaderView
import com.duyvv.kmadoc.ui.component.BaseInputTextFiled
import com.duyvv.kmadoc.util.SharePreferenceExt

@Composable
fun ChangeInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeInfoViewModel,
    onClickBack: () -> Unit,
    onClickUpdateInfo: () -> Unit
) {
    Box {
        BaseHeaderView(
            modifier = modifier.align(Alignment.TopCenter),
            "Thông tin cá nhân",
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
        BaseInputTextFiled(
            hint = if (SharePreferenceExt.isAdminAccount) "Email" else "Mã sinh viên",
            value = viewModel.userInfo.collectAsStateWithLifecycle().value,
            onValueChange = {
                viewModel.updateStudentCode(it)
            },
            singleLine = true,
            startIconRes = if (SharePreferenceExt.isAdminAccount) R.drawable.ic_mail else R.drawable.ic_user_id
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
        BaseButton(
            modifier = modifier.height(65.dp),
            text = "Cập nhật thông tin",
            onClickButton = onClickUpdateInfo,
            enabled = true
        )
    }
}