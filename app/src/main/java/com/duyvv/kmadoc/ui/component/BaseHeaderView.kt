package com.duyvv.kmadoc.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun BaseHeaderView(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickBack: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            tint = colorResource(R.color.primary_color),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
                .nonAimClickable { onClickBack.invoke() }
        )
        Text(
            "Đăng ký tài khoản",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary_color),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}