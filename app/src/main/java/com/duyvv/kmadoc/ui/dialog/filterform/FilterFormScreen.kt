package com.duyvv.kmadoc.ui.dialog.filterform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.ui.component.BaseButton
import com.duyvv.kmadoc.util.nonAimClickable

@Composable
fun FilterFormScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel,
    onClickBack: () -> Unit,
    onClickApply: () -> Unit
) {
    val listFormType by viewModel.listType.collectAsStateWithLifecycle()
    val listStatus by viewModel.listStatus.collectAsStateWithLifecycle()
    val isSelectedAllType by viewModel.isSelectAllType.collectAsStateWithLifecycle(false)
    val isSelectedAllStatus by viewModel.isSelectAllStatus.collectAsStateWithLifecycle(false)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close_primary),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .nonAimClickable {
                        onClickBack.invoke()
                    }
            )
            Text(
                "Lọc đơn",
                color = colorResource(R.color.primary_color),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    "Loại đơn",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(10.dp))
            }
            item {
                FilterItem(
                    item = FilterModel(id = "1", title = "Tất cả", isSelected = isSelectedAllType),
                    onClick = {
                        viewModel.onClickSelectedAllType(it)
                    }
                )
            }
            itemsIndexed(
                items = listFormType,
                key = { _, item ->
                    item.id
                }
            ) { index, item ->
                FilterItem(
                    item = item,
                    onClick = {
                        viewModel.onClickItemFilterType(index, item)
                    }
                )
            }
            item {
                Spacer(Modifier.height(10.dp))
                Text(
                    "Trạng thái",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(10.dp))
            }
            item {
                FilterItem(
                    item = FilterModel(
                        id = "1",
                        title = "Tất cả",
                        isSelected = isSelectedAllStatus
                    ),
                    onClick = {
                        viewModel.onClickSelectedAllStatus(it)
                    }
                )
            }
            itemsIndexed(
                items = listStatus,
                key = { _, item ->
                    item.id
                }
            ) { index, item ->
                FilterItem(
                    item = item,
                    onClick = {
                        viewModel.onClickItemFilterStatus(index, item)
                    }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        BaseButton(
            modifier = modifier.padding(horizontal = 16.dp),
            text = "Áp dụng",
            onClickButton = {
                onClickApply()
            },
            enabled = true
        )
    }
}

@Composable
fun FilterItem(
    item: FilterModel,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.nonAimClickable {
            onClick.invoke(!item.isSelected)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.height(35.dp),
            checked = item.isSelected,
            onCheckedChange = {
                onClick.invoke(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.primary_color)
            )
        )
        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 15.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Pre1(modifier: Modifier = Modifier) {
    FilterItem(
        item = FilterModel(title = "Tất cả", isSelected = true, id = "1"),
        onClick = {}
    )
}