package com.duyvv.kmadoc.ui.list_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.model.FormStatus
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.data.model.Student
import com.duyvv.kmadoc.ui.component.RoundedDropdownView
import com.duyvv.kmadoc.ui.list_form.component.SearchTextField
import com.duyvv.kmadoc.util.DateTimeExt
import com.duyvv.kmadoc.util.formatFromDateToDateString
import com.duyvv.kmadoc.util.nonAimClickable


@Composable
fun ListFormScreen(
    modifier: Modifier = Modifier,
    viewModel: ListFormViewModel,
    onClickItem: (FormModel) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickFilterTime: () -> Unit = {},
    onClickFilterType: () -> Unit = {},
) {
    val listForm by viewModel.listForm.collectAsStateWithLifecycle(emptyList())
    var isFocusSearch by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .nonAimClickable {
                isFocusSearch = false
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onClickBack.invoke()
                    }
            )
            Text(
                "Danh sách đơn",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(20.dp))
        SearchTextField(
            value = viewModel.keySearch.collectAsStateWithLifecycle().value,
            onValueChange = {
                viewModel.updateKeySearch(it)
            },
            modifier = modifier.padding(horizontal = 10.dp),
            onFocusChange = {
                isFocusSearch = it
            },
            isFocus = isFocusSearch
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedDropdownView(
                text = formatFromDateToDateString(
                    viewModel.startDate.collectAsStateWithLifecycle().value,
                    viewModel.endDate.collectAsStateWithLifecycle().value
                ),
                onClick = {
                    onClickFilterTime.invoke()
                    isFocusSearch = false
                }
            )
            RoundedDropdownView(
                text = "Loại đơn",
                onClick = {
                    onClickFilterType.invoke()
                    isFocusSearch = false
                }
            )
        }
        if (listForm.isEmpty()) {
            Spacer(Modifier.height(30.dp))
            Text(
                "Không có đơn nào!",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        } else {
            Spacer(Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 5.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(
                    items = listForm,
                    key = { index, _ ->
                        index
                    }
                ) { index, item ->
                    FormItem(item = item, modifier = modifier, onClickItem = onClickItem)
                }
            }
        }
    }
}

@Composable
fun FormItem(
    item: FormModel,
    modifier: Modifier = Modifier,
    onClickItem: (FormModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(R.color.gray), shape = RoundedCornerShape(16.dp))
            .clickable { onClickItem.invoke(item) }
            .padding(vertical = 15.dp)
            .padding(start = 10.dp, end = 8.dp),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.formType?.type?.titleVn ?: "Không xác định",
                    color = Color.Black,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_circle),
                    tint = colorResource(R.color.textSecondary),
                    modifier = Modifier.size(5.dp),
                    contentDescription = null
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = DateTimeExt.convertToDate(item.createdAt),
                    color = colorResource(R.color.textSecondary),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.student.username,
                color = colorResource(R.color.textSecondary),
                fontSize = 13.sp,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(
                        color = when (item.status) {
                            FormStatus.APPROVED -> colorResource(R.color.colorApproved)
                            FormStatus.DENIED -> Color.Red
                            FormStatus.STAGING -> colorResource(R.color.colorStaging)
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 7.dp, vertical = 5.dp),
                text = item.status.title,
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "Arrow",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun Pre1(modifier: Modifier = Modifier) {
    FormItem(
        item = FormModel(
            createdAt = "2024-12-14T10:50:47.757Z",
            student = Student(username = "Duy Dep trai"),
            status = FormStatus.STAGING,
            formType = FormTypeModel(id = "1", title = "Đơn xin thôi học", type = FormType.THOI_HOC)
        ),
        onClickItem = {

        }
    )
}

@Composable
@Preview(showBackground = true)
fun Pre2(modifier: Modifier = Modifier) {
    RoundedDropdownView(
        text = "Thời gian"
    )
    RoundedDropdownView(
        text = "Loại đơn"
    )
}