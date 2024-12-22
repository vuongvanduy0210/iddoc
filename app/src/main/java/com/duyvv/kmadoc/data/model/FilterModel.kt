package com.duyvv.kmadoc.data.model

data class FilterModel(
    val id: String,
    val title: String,
    var isSelected: Boolean = false
)