package com.duyvv.iddoc.data.dto.request


import com.google.gson.annotations.SerializedName

data class OCRFormRequest(
    @SerializedName("application_name")
    val applicationName: String = "",
    @SerializedName("image_urls")
    val imageUrls: List<String> = listOf()
)