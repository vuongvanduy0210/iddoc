package com.duyvv.kmadoc.data.model

import java.io.Serializable

data class CreateFormModel(
    val formDate: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val major: String = "",
    val mClass: String = "",
    val studentId: String = "",
    val birthday: String = "",
    val gender: String = "",
    val address: String = "",
    var personalId: String = "",
    var dateCCCD: String = "",
    var addressCCCD: String = "",
    var pronouncementNumber: String = "",
    var signDate: String = "",
    var reservedDate: String = "",
    var reservedMonth: String = "",
    var continueStudyDate: String = "",
    var semester: String = "",
    var schoolYear: String = "",
    var parentName: String = "",
    var parentPhone: String = "",
    var parentAddress: String = "",
    var dropOffDate: String = "",
    var dropOffReason: String = "",
    var nativeCountry: String = "",
    var bhytContent: String = ""
) : Serializable