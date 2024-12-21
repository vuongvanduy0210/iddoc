package com.duyvv.iddoc.ui.list_form.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duyvv.iddoc.R

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Tìm kiếm"
) {
    // Quản lý focus
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    // Đổi màu viền khi focus
    val borderColor = if (isFocused) colorResource(R.color.color_enabled) else Color.Gray

    Box {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .focusRequester(focusRequester)
                .focusTarget()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .border(1.dp, borderColor, RoundedCornerShape(16.dp)) // Đổi màu viền
                        .padding(start = 10.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )

                    // Placeholder
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                hint,
                                color = Color(0xFF787878),
                                fontSize = 14.sp,
                                modifier = Modifier // Khoảng cách với icon
                            )
                        }
                        innerTextField() // Text input area
                    }
                }
            }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun Pre(modifier: Modifier = Modifier) {
    SearchTextField(
        "",
        onValueChange = {},
        modifier = modifier
    )
}