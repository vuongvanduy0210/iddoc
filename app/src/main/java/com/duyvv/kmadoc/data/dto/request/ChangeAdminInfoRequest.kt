package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class ChangeAdminInfoRequest(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("username")
    val username: String = ""
)