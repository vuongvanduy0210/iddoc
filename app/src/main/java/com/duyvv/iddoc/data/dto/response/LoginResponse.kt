package com.duyvv.iddoc.data.dto.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("message")
    val message: String? = null
)

data class Info(
    @SerializedName("accessToken")
    val accessToken: String? = null
)