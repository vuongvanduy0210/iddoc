package com.duyvv.iddoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class UpdateStatusFormRequest(
    @SerializedName("status")
    val status: String = ""
)