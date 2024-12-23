package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class ChangePassRequest(
    @SerializedName("lastPassword")
    val lastPassword: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("retypePassword")
    val retypePassword: String = ""
)