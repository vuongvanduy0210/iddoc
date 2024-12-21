package com.duyvv.iddoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class BaseCRUDFormResponse(
    @SerializedName("info")
    val info: BaseCRUDResponse? = null,
    @SerializedName("message")
    val message: String? = null
)

data class BaseCRUDResponse(
    @SerializedName("formId")
    val formId: String? = null
)