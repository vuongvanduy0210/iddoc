package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName

data class CreateContinueStudyFormRequest(
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION")
    var personalCode: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE")
    var dateCCCD: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION")
    var addressCCCD: String = "",
    @SerializedName("PRONOUNCEMENT_NUMBER")
    val pronouncementNumber: String,
    @SerializedName("SIGNED_DATE")
    val signDate: String,
    @SerializedName("RESERVED_DATE")
    val reservedDate: String,
    @SerializedName("RESERVED_MONTH")
    val reservedMonth: String,
    @SerializedName("CONTINUE_STUDY_DATE")
    val continueDate: String,
    @SerializedName("SEMESTER")
    val semester: String,
    @SerializedName("SCHOOL_YEAR")
    val studyYear: String,
) : BaseCreateFormRequest()
