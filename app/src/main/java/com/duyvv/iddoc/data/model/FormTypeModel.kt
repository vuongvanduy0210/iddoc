package com.duyvv.iddoc.data.model

import java.io.Serializable

data class FormTypeModel(
    val id: String,
    val title: String,
    val type: FormType
) : Serializable
