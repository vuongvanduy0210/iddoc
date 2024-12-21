package com.duyvv.iddoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class OCRFormResponse(
    @SerializedName("extracted_text")
    val extractedText: List<ExtractedTextResponse>? = null
)

data class ExtractedTextResponse(
    @SerializedName("data_type")
    val dataType: String? = null,
    @SerializedName("field_type")
    val fieldType: String? = null,
    @SerializedName("name")
    val fieldName: String? = null,
    @SerializedName("text")
    val content: String? = null
)