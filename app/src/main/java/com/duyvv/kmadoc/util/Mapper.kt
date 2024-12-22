package com.duyvv.kmadoc.util

import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.request.CreateContinueStudyFormRequest
import com.duyvv.kmadoc.data.dto.request.CreateDropOutFormRequest
import com.duyvv.kmadoc.data.dto.request.CreateStudentHealthFormRequest
import com.duyvv.kmadoc.data.dto.response.ExtractedTextResponse
import com.duyvv.kmadoc.data.dto.response.FieldDTO
import com.duyvv.kmadoc.data.dto.response.FormResponse
import com.duyvv.kmadoc.data.dto.response.FormResponseDTO
import com.duyvv.kmadoc.data.dto.response.OCRFormResponse
import com.duyvv.kmadoc.data.dto.response.StatisticDTO
import com.duyvv.kmadoc.data.dto.response.StudentDTO
import com.duyvv.kmadoc.data.model.CreateFormModel
import com.duyvv.kmadoc.data.model.Field
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.model.FormStatus
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.data.model.StatisticModel
import com.duyvv.kmadoc.data.model.Student


fun CreateFormModel.toBaseCreateFormRequest(): BaseCreateFormRequest {
    return BaseCreateFormRequest(
        fullName = fullName,
        phoneNumber = phoneNumber,
        major = major,
        mClass = mClass,
        studentCode = studentId,
        birthday = birthday,
        gender = gender,
        address = address,
        formDate = formDate
    )
}

fun CreateFormModel.toDropOutRequest(): CreateDropOutFormRequest {
    return CreateDropOutFormRequest(
        parentName = parentName,
        parentPhone = parentPhone,
        parentAddress = parentAddress,
        dropOffDate = dropOffDate,
        dropOffReason = dropOffReason
    ).apply {
        fullName = this@toDropOutRequest.fullName
        phoneNumber = this@toDropOutRequest.phoneNumber
        major = this@toDropOutRequest.major
        mClass = this@toDropOutRequest.mClass
        studentCode = this@toDropOutRequest.studentId
        birthday = this@toDropOutRequest.birthday
        gender = this@toDropOutRequest.gender
        personalCode = this@toDropOutRequest.personalId
        dateCCCD = this@toDropOutRequest.dateCCCD
        addressCCCD = this@toDropOutRequest.addressCCCD
        address = this@toDropOutRequest.address
        formDate = this@toDropOutRequest.formDate
    }
}

fun CreateFormModel.toStudentHealthRequest(): CreateStudentHealthFormRequest {
    return CreateStudentHealthFormRequest(
        hometown = nativeCountry,
        content = bhytContent
    ).apply {
        fullName = this@toStudentHealthRequest.fullName
        phoneNumber = this@toStudentHealthRequest.phoneNumber
        major = this@toStudentHealthRequest.major
        mClass = this@toStudentHealthRequest.mClass
        studentCode = this@toStudentHealthRequest.studentId
        birthday = this@toStudentHealthRequest.birthday
        gender = this@toStudentHealthRequest.gender
        address = this@toStudentHealthRequest.address
        formDate = this@toStudentHealthRequest.formDate
    }
}

fun CreateFormModel.toContinueStudyRequest(): CreateContinueStudyFormRequest {
    return CreateContinueStudyFormRequest(
        pronouncementNumber = pronouncementNumber,
        signDate = signDate,
        reservedDate = reservedDate,
        reservedMonth = reservedMonth,
        continueDate = continueStudyDate,
        semester = semester,
        studyYear = schoolYear
    ).apply {
        fullName = this@toContinueStudyRequest.fullName
        phoneNumber = this@toContinueStudyRequest.phoneNumber
        major = this@toContinueStudyRequest.major
        mClass = this@toContinueStudyRequest.mClass
        studentCode = this@toContinueStudyRequest.studentId
        birthday = this@toContinueStudyRequest.birthday
        gender = this@toContinueStudyRequest.gender
        personalCode = this@toContinueStudyRequest.personalId
        dateCCCD = this@toContinueStudyRequest.dateCCCD
        addressCCCD = this@toContinueStudyRequest.addressCCCD
        address = this@toContinueStudyRequest.address
        formDate = this@toContinueStudyRequest.formDate
    }
}

fun FormModel.toBaseCreateFormRequest(): BaseCreateFormRequest {
    return BaseCreateFormRequest(
        formDate = getContentByFieldName(APPLICATION_DISTRICT_DATE),
        fullName = getContentByFieldName(STUDENT_NAME),
        phoneNumber = getContentByFieldName(STUDENT_PHONE_NUMBER),
        major = getContentByFieldName(MAJOR),
        mClass = getContentByFieldName(STUDENT_CLASS),
        studentCode = getContentByFieldName(STUDENT_ID),
        birthday = getContentByFieldName(STUDENT_BIRTH_DATE),
        gender = getContentByFieldName(STUDENT_GENDER),
        address = getContentByFieldName(PERMANENT_RESIDENT)
    )
}

fun FormModel.toDropOutRequest(): CreateDropOutFormRequest {
    return CreateDropOutFormRequest(
        parentName = getContentByFieldName(PARENT_NAME),
        parentPhone = getContentByFieldName(PARENT_PHONE_NUMBER),
        parentAddress = getContentByFieldName(PARENT_CURRENT_RESIDENT),
        dropOffDate = getContentByFieldName(DROP_OFF_DATE),
        dropOffReason = getContentByFieldName(DROP_OFF_REASON)
    ).apply {
        formDate = getContentByFieldName(APPLICATION_DISTRICT_DATE)
        fullName = getContentByFieldName(STUDENT_NAME)
        phoneNumber = getContentByFieldName(STUDENT_PHONE_NUMBER)
        major = getContentByFieldName(MAJOR)
        mClass = getContentByFieldName(STUDENT_CLASS)
        studentCode = getContentByFieldName(STUDENT_ID)
        birthday = getContentByFieldName(STUDENT_BIRTH_DATE)
        gender = getContentByFieldName(STUDENT_GENDER)
        personalCode = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION)
        dateCCCD = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE)
        addressCCCD = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION)
        address = getContentByFieldName(PERMANENT_RESIDENT)
    }
}

fun FormModel.toStudentHealthRequest(): CreateStudentHealthFormRequest {
    return CreateStudentHealthFormRequest(
        hometown = getContentByFieldName(NATIVE_COUNTRY),
        content = getContentByFieldName(CONTENT)
    ).apply {
        formDate = getContentByFieldName(APPLICATION_DISTRICT_DATE)
        fullName = getContentByFieldName(STUDENT_NAME)
        phoneNumber = getContentByFieldName(STUDENT_PHONE_NUMBER)
        major = getContentByFieldName(MAJOR)
        mClass = getContentByFieldName(STUDENT_CLASS)
        studentCode = getContentByFieldName(STUDENT_ID)
        birthday = getContentByFieldName(STUDENT_BIRTH_DATE)
        gender = getContentByFieldName(STUDENT_GENDER)
        address = getContentByFieldName(PERMANENT_RESIDENT)
    }
}

fun FormModel.toContinueStudyRequest(): CreateContinueStudyFormRequest {
    return CreateContinueStudyFormRequest(
        pronouncementNumber = getContentByFieldName(PRONOUNCEMENT_NUMBER),
        signDate = getContentByFieldName(SIGNED_DATE),
        reservedDate = getContentByFieldName(RESERVED_DATE),
        reservedMonth = getContentByFieldName(RESERVED_MONTH),
        continueDate = getContentByFieldName(CONTINUE_STUDY_DATE),
        semester = getContentByFieldName(SEMESTER),
        studyYear = getContentByFieldName(SCHOOL_YEAR)
    ).apply {
        formDate = getContentByFieldName(APPLICATION_DISTRICT_DATE)
        fullName = getContentByFieldName(STUDENT_NAME)
        phoneNumber = getContentByFieldName(STUDENT_PHONE_NUMBER)
        major = getContentByFieldName(MAJOR)
        mClass = getContentByFieldName(STUDENT_CLASS)
        studentCode = getContentByFieldName(STUDENT_ID)
        birthday = getContentByFieldName(STUDENT_BIRTH_DATE)
        gender = getContentByFieldName(STUDENT_GENDER)
        personalCode = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION)
        dateCCCD = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_VALID_DATE)
        addressCCCD = getContentByFieldName(STUDENT_CITIZEN_IDENTIFICATION_ISSUED_LOCATION)
        address = getContentByFieldName(PERMANENT_RESIDENT)
    }
}

fun FormResponse.toFormTypeModel(): FormTypeModel {
    return FormTypeModel(
        id = this.categoryId ?: "",
        title = this.title ?: "",
        type = FormType.fromTitleEn(this.title ?: "") ?: FormType.THOI_HOC
    )
}

fun OCRFormResponse.toDomainModel(): FormModel {
    return FormModel(
        fields = extractedText?.map { it.toDomainModel() } ?: emptyList()
    )
}

fun ExtractedTextResponse.toDomainModel(): Field {
    return Field(
        name = fieldName.orEmpty(),
        value = content.orEmpty()
    )
}

fun FormResponseDTO.toDomainModel(): FormModel {
    return FormModel(
        formType = this.formTypeModel?.toFormTypeModel(),
        categoryId = this.categoryId ?: "",
        createdAt = this.createdAt ?: "",
        deletedAt = this.deletedAt,
        fields = this.fields?.map { it.toDomainModel() } ?: emptyList(),
        personalFormId = this.personalFormId ?: "",
        signature = this.signature,
        status = when (this.status) {
            "APPROVED" -> FormStatus.APPROVED
            "DENIED" -> FormStatus.DENIED
            else -> FormStatus.STAGING
        },
        student = this.student?.toDomainModel() ?: Student(),
        studentId = this.studentId ?: "",
        updatedBy = this.updatedBy ?: ""
    )
}

fun FieldDTO.toDomainModel(): Field {
    return Field(
        fieldId = this.fieldId ?: "",
        name = this.name ?: "",
        personalFormId = this.personalFormId ?: "",
        value = this.value ?: ""
    )
}

fun StudentDTO.toDomainModel(): Student {
    return Student(
        studentCode = this.studentCode ?: "",
        studentId = this.studentId ?: "",
        username = this.username ?: ""
    )
}

fun StatisticDTO.toStatisticModel(): StatisticModel {
    return StatisticModel(
        totalForm = this@toStatisticModel.totalForms ?: 0f,
        categoryId = this@toStatisticModel.categoryId ?: "",
        formType = FormType.entries.find { it.id == this@toStatisticModel.categoryId }
            ?: FormType.THOI_HOC
    )
}

