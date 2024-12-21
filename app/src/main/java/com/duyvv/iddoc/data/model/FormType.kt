package com.duyvv.iddoc.data.model

enum class FormType(val titleVn: String, val titleEn: String) {
    THOI_HOC("Đơn xin thôi học", "drop-out-school-application"),
    CAP_LAI_THE_SINH_VIEN("Đơn xin cấp lại thẻ sinh viên", "reissued-student-card-application"),
    CAP_LAI_THE_BHYT(
        "Đơn xin cấp lại thẻ bảo hiểm y tế",
        "reissued-student-health-insurrance-application"
    ),
    XIN_TIEP_TUC_HOC("Đơn xin tiếp tục học", "continue-study-application");

    companion object {
        fun fromTitleEn(titleEn: String): FormType? {
            return entries.find { it.titleEn == titleEn }
        }
    }
}