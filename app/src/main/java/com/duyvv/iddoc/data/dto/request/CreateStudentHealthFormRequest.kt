package com.duyvv.iddoc.data.dto.request

import com.google.gson.annotations.SerializedName

data class CreateStudentHealthFormRequest(
    @SerializedName("NATIVE_COUNTRY")
    val hometown: String,
    @SerializedName("CONTENT")
    val content: String,
) : BaseCreateFormRequest()
