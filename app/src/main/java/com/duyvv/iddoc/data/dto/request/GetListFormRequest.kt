package com.duyvv.iddoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class GetListFormRequest(
    @SerializedName("currentPage")
    val currentPage: Int = 0,
    @SerializedName("keySearch")
    val keySearch: String = "",
    @SerializedName("limit")
    val limit: Int = 0
)