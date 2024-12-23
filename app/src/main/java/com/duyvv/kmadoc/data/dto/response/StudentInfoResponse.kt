package com.duyvv.kmadoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class StudentInfoResponse(
    @SerializedName("info")
    val info: StudentInfoDTO? = null,
    @SerializedName("message")
    val message: String? = null
)

data class StudentInfoDTO(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: Any? = null,
    @SerializedName("studentCode")
    val studentCode: String? = null,
    @SerializedName("studentId")
    val studentId: String? = null,
    @SerializedName("username")
    val username: String? = null
)