package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName


data class AdminLoginRequest(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("password")
    val password: String = ""
)