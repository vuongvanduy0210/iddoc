package com.duyvv.iddoc.data.dto.response


import com.google.gson.annotations.SerializedName

data class ListFormResponse(
    @SerializedName("info")
    val info: List<FormResponseDTO>? = null,
    @SerializedName("message")
    val message: String? = null
)

data class FormResponseDTO(
    @SerializedName("category")
    val formTypeModel: FormResponse? = null,
    @SerializedName("categoryId")
    val categoryId: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: Any? = null,
    @SerializedName("fields")
    val fields: List<FieldDTO>? = null,
    @SerializedName("personalFormId")
    val personalFormId: String? = null,
    @SerializedName("signature")
    val signature: Any? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("student")
    val student: StudentDTO? = null,
    @SerializedName("studentId")
    val studentId: String? = null,
    @SerializedName("updatedBy")
    val updatedBy: String? = null
)

data class FieldDTO(
    @SerializedName("fieldId")
    val fieldId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("personalFormId")
    val personalFormId: String? = null,
    @SerializedName("value")
    val value: String? = null
)

data class StudentDTO(
    @SerializedName("studentCode")
    val studentCode: String? = null,
    @SerializedName("studentId")
    val studentId: String? = null,
    @SerializedName("username")
    val username: String? = null
)