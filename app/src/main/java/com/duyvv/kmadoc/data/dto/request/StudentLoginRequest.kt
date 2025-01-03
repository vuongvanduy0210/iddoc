package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName


data class StudentLoginRequest(
    @SerializedName("studentCode")
    val studentCode: String = "",
    @SerializedName("password")
    val password: String = ""
)