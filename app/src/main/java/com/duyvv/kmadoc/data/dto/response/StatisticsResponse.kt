package com.duyvv.kmadoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class StatisticsResponse(
    @SerializedName("info")
    val data: List<StatisticDTO>? = null,
    @SerializedName("message")
    val message: String? = null
)

data class StatisticDTO(
    @SerializedName("categoryId")
    val categoryId: String? = null,
    @SerializedName("totalForms")
    val totalForms: Float? = null
)