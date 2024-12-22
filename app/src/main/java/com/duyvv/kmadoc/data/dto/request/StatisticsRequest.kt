package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class StatisticsRequest(
    @SerializedName("fromDate")
    val fromDate: String = "",
    @SerializedName("toDate")
    val toDate: String = ""
)