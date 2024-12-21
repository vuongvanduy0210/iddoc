package com.duyvv.kmadoc.data.model

import java.io.Serializable

data class FormTypeModel(
    val id: String,
    val title: String,
    val type: FormType
) : Serializable
