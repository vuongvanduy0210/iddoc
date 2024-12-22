package com.duyvv.kmadoc.data.model

enum class FormType(val id: String, val titleVn: String, val titleEn: String) {
    THOI_HOC(
        "4719bd82-40e8-4ffc-a0f5-4424884e129a",
        "Đơn xin thôi học",
        "drop-out-school-application"
    ),
    CAP_LAI_THE_SINH_VIEN(
        "4b1d8501-2eb1-4665-99c3-4a7f6ac84c64",
        "Đơn xin cấp lại thẻ sinh viên",
        "reissued-student-card-application"
    ),
    CAP_LAI_THE_BHYT(
        "5fc5a90f-0037-43fe-8a84-615a774e5f57",
        "Đơn xin cấp lại thẻ bảo hiểm y tế",
        "reissued-student-health-insurrance-application"
    ),
    XIN_TIEP_TUC_HOC(
        "6bf07c96-649c-4a15-b26c-d2eb0894f2ad",
        "Đơn xin tiếp tục học",
        "continue-study-application"
    );

    companion object {
        fun fromTitleEn(titleEn: String): FormType? {
            return entries.find { it.titleEn == titleEn }
        }
    }
}