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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duyvv.kmadoc.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "Mật khẩu",
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    onClickEndIcon: () -> Unit = {},
    onClickStartIcon: () -> Unit = {},
    endIconRes: Int? = null,
    startIconRes: Int? = null,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
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
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(
                    painter = painterResource(if (isPasswordVisible) R.drawable.ic_visible_off else R.drawable.ic_visibility),
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        )
    )
}