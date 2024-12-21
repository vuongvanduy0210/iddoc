package com.duyvv.kmadoc.ui.selectform

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel

@Composable
fun SelectFormTypeScreen(
    modifier: Modifier = Modifier,
    viewModel: FormTypeViewModel,
    onClickItem: (FormTypeModel) -> Unit
) {
    val listItem by viewModel.listForm.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
            .padding(vertical = 20.dp, horizontal = 36.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Chọn loại đơn",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        listItem.forEach {
            TypeItem(item = it, onClickItem = onClickItem)
        }
    }
}

@Composable
fun TypeItem(
    item: FormTypeModel,
    modifier: Modifier = Modifier,
    onClickItem: (FormTypeModel) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFF2C3D7A), shape = RoundedCornerShape(10.dp))
            .clickable { onClickItem.invoke(item) }
            .padding(vertical = 15.dp)
            .padding(start = 10.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.type.titleVn,
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "Arrow",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun Pre1(modifier: Modifier = Modifier) {
    TypeItem(
        item = FormTypeModel(
            id = "1",
            title = "Đơn xin thôi học",
            type = FormType.THOI_HOC
        ),
        onClickItem = {

        }
    )
}