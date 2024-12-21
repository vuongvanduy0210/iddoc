package com.duyvv.iddoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class UpdateStatusFormResponse(
    @SerializedName("info")
    val info: FormResponseDTO? = null,
    @SerializedName("message")
    val message: String? = null
)