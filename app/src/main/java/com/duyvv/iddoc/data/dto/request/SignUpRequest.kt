package com.duyvv.iddoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("password")
    val password: String? = "",
    @SerializedName("studentCode")
    val studentCode: String? = "",
    @SerializedName("username")
    val username: String? = ""
)