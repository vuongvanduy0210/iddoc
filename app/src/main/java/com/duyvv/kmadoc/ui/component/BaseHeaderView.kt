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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun BaseHeaderView(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickBack: () -> Unit,
    iconClose: Int = R.drawable.ic_back
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Icon(
            painter = painterResource(iconClose),
            tint = colorResource(R.color.primary_color),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
                .nonAimClickable { onClickBack.invoke() }
        )
        Text(
            title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary_color),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Pre(modifier: Modifier = Modifier) {
    BaseHeaderView(
        title = "Đăng ký tài khoản",
        onClickBack = {

        },
        iconClose = R.drawable.ic_close_primary
    )
}