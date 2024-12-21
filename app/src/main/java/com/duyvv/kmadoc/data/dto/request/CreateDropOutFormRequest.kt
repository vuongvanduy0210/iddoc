package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName

data class CreateDropOutFormRequest(
    @SerializedName("PARENT_NAME")
    val parentName: String,
    @SerializedName("PARENT_PHONE_NUMBER")
    val parentPhone: String,
    @SerializedName("PARENT_CURRENT_RESIDENT")
    val parentAddress: String,
    @SerializedName("DROP_OFF_DATE")
    val dropOffDate: String,
    @SerializedName("DROP_OFF_REASON")
    val dropOffReason: String,
) : BaseCreateFormRequest()
