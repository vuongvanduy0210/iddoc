package com.duyvv.iddoc.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit = {},
    enabled: Boolean = false,
    containerColor: Color = Color(0xFF2C3D7A),
    text: String = "Tiếp tục"
) {
    Button(
        onClick = { onClickButton.invoke() },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        enabled = enabled,
        content = {
            Text(text, fontSize = 16.sp)
        },
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFA6A6A6),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    )
}