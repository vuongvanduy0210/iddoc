package com.duyvv.kmadoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class FormTypesResponse(
    @SerializedName("info")
    val data: List<FormResponse>? = null,
    @SerializedName("message")
    val message: String? = null
)

data class FormResponse(
    @SerializedName("categoryId")
    val categoryId: String? = null,
    @SerializedName("title")
    val title: String? = null
)