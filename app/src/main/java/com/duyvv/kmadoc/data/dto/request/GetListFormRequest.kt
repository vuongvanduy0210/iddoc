package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class GetListFormRequest(
    @SerializedName("currentPage")
    val currentPage: Int = 0,
    @SerializedName("keySearch")
    val keySearch: String = "",
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("status")
    val status: List<String> = listOf(),
    @SerializedName("categoryIds")
    val types: List<String> = listOf(),
    @SerializedName("fromDate")
    val startDate: String = "",
    @SerializedName("toDate")
    val endDate: String = ""
)