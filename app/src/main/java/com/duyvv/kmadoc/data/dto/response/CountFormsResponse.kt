package com.duyvv.kmadoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class CountFormsResponse(
    @SerializedName("info")
    val data: CountFormsDTO? = null,
    @SerializedName("message")
    val message: String? = null
)

data class CountFormsDTO(
    @SerializedName("totalForms")
    val totalForms: Int? = null
)