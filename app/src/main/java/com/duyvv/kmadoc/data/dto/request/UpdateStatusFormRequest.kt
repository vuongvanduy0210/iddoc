package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class UpdateStatusFormRequest(
    @SerializedName("status")
    val status: String = ""
)