package com.duyvv.kmadoc.data.model


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("exp")
    val exp: Int = 0,
    @SerializedName("iat")
    val iat: Int = 0,
    @SerializedName("role")
    val role: String = "",
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("username")
    val username: String = ""
)