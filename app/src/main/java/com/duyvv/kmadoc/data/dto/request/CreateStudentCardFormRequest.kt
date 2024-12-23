package com.duyvv.kmadoc.data.dto.request

import com.google.gson.annotations.SerializedName

data class CreateStudentCardFormRequest(
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION")
    var personalCode: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE")
    var dateCCCD: String = "",
    @SerializedName("STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION")
    var addressCCCD: String = ""
) : BaseCreateFormRequest()
