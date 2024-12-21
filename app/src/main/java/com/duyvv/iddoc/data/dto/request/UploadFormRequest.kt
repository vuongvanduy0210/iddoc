package com.duyvv.iddoc.data.dto.request

import com.google.gson.annotations.SerializedName

data class UploadFormRequest(
    @SerializedName("categoryId")
    var categoryId: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("fields")
    var fields: BaseCreateFormRequest
)
