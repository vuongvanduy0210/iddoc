package com.duyvv.kmadoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class ChangeStudentInfoRequest(
    @SerializedName("studentCode")
    val studentCode: String = "",
    @SerializedName("username")
    val username: String = ""
)