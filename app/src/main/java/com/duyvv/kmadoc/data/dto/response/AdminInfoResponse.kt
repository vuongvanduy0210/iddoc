package com.duyvv.kmadoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class AdminInfoResponse(
    @SerializedName("info")
    val info: AdminInfoDTO? = null,
    @SerializedName("message")
    val message: String? = null
)

data class AdminInfoDTO(
    @SerializedName("adminId")
    val adminId: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: Any? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("username")
    val username: String? = null
)