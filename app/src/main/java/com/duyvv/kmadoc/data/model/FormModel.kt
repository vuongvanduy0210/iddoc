package com.duyvv.kmadoc.data.model

import java.io.Serializable

data class FormModel(
    val categoryId: String = "",
    val createdAt: String = "",
    val deletedAt: Any? = null,
    val fields: List<Field> = emptyList(),
    val personalFormId: String = "",
    val signature: Any? = null,
    val status: FormStatus = FormStatus.STAGING,
    val student: Student = Student(),
    val studentId: String = "",
    val updatedBy: String = "",
    val formType: FormTypeModel? = null
) : Serializable

data class Field(
    val fieldId: String = "",
    val name: String = "",
    val personalFormId: String = "",
    val value: String = ""
) : Serializable

data class Student(
    val studentCode: String = "",
    val studentId: String = "",
    val username: String = ""
) : Serializable

enum class FormStatus(val title: String) {
    APPROVED("Chấp thuận"), DENIED("Từ chối"), STAGING("Đang chờ")
}