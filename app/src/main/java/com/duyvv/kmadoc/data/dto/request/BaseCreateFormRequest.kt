package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName

open class BaseCreateFormRequest(
    @SerializedName("APPLICATION_DISTRICT_DATE")
    var formDate: String = "",
    @SerializedName("STUDENT_NAME")
    var fullName: String = "",
    @SerializedName("STUDENT_PHONE_NUMBER")
    var phoneNumber: String = "",
    @SerializedName("MAJOR")
    var major: String = "",
    @SerializedName("STUDENT_CLASS")
    var mClass: String = "",
    @SerializedName("STUDENT_ID")
    var studentCode: String = "",
    @SerializedName("STUDENT_BIRTH_DATE")
    var birthday: String = "",
    @SerializedName("STUDENT_GENDER")
    var gender: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION")
    var personalCode: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE")
    var dateCCCD: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION")
    var addressCCCD: String = "",
    @SerializedName("PERMANENT_RESIDENT")
    var address: String = ""
)
