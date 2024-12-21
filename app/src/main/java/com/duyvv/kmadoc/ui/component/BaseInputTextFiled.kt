package com.duyvv.kmadoc.ui.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
@Preview(showBackground = true)
fun Pre1(modifier: Modifier = Modifier) {
    BaseInputTextFiled(
        onValueChange = {},
        hint = "Họ và tên"
    )
}