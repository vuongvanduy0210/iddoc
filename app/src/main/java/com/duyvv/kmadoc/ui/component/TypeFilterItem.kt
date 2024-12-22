package com.duyvv.kmadoc.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun RoundedDropdownView(
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = colorResource(R.color.gray600),
                shape = RoundedCornerShape(10.dp)
            )
            .nonAimClickable { onClick() }
            .padding(vertical = 4.dp)
            .padding(start = 10.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Color.Black, // Màu chữ trắng
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_drop_down),
            contentDescription = "Dropdown Icon",
            tint = colorResource(R.color.gray600),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Pre2(modifier: Modifier = Modifier) {
    RoundedDropdownView(
        text = "Thời gian"
    )
}