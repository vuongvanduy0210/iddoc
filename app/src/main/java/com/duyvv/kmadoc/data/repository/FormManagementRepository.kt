package com.duyvv.kmadoc.data.repository

import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.request.GetListFormRequest
import com.duyvv.kmadoc.data.dto.request.StatisticsRequest
import com.duyvv.kmadoc.data.dto.request.UpdateStatusFormRequest
import com.duyvv.kmadoc.data.dto.request.UploadFormRequest
import com.duyvv.kmadoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.kmadoc.data.dto.response.CountFormsResponse
import com.duyvv.kmadoc.data.dto.response.CreateFormResponse
import com.duyvv.kmadoc.data.dto.response.FormTypesResponse
import com.duyvv.kmadoc.data.dto.response.ListFormResponse
import com.duyvv.kmadoc.data.dto.response.StatisticsResponse
import com.duyvv.kmadoc.data.dto.response.UpdateStatusFormResponse
import com.duyvv.kmadoc.data.remote.service.FormManagementService
import com.duyvv.kmadoc.util.DEFAULT_ERROR_MESSAGE
import javax.inject.Inject

interface FormManagementRepository {
    suspend fun createForm(
        studentId: String = "",
        categoryId: String = "",
        body: BaseCreateFormRequest
    ): Result<CreateFormResponse>

    suspend fun getFormTypes(): Result<FormTypesResponse>

    suspend fun getListForm(
        studentId: String?,
        categoryId: String?,
        request: GetListFormRequest
    ): Result<ListFormResponse>

    suspend fun updateStatusForm(
        formId: String,
        request: UpdateStatusFormRequest
    ): Result<UpdateStatusFormResponse>

    suspend fun uploadForm(
        adminId: String,
        request: UploadFormRequest
    ): Result<BaseCRUDFormResponse>

    suspend fun updateForm(
        formId: String,
        request: BaseCreateFormRequest
    ): Result<BaseCRUDFormResponse>

    suspend fun deleteForm(formId: String): Result<BaseCRUDFormResponse>

    suspend fun getStatisticsForm(request: StatisticsRequest): Result<StatisticsResponse>

    suspend fun countForms(): Result<CountFormsResponse>
}

class FormManagementRepositoryImpl @Inject constructor(
    private val service: FormManagementService
) : FormManagementRepository {

    override suspend fun createForm(
        studentId: String,
        categoryId: String,
        body: BaseCreateFormRequest
    ): Result<CreateFormResponse> {
        return runCatching {
            service.createForm(studentId, categoryId, body)
        }.mapCatching { response ->
            if (response.message == "Successfull") {
                response
            } else {
                throw Exception(response.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun getFormTypes(): Result<FormTypesResponse> {
        return runCatching {
            service.getFormTypes()
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun getListForm(
        studentId: String?,
        categoryId: String?,
        request: GetListFormRequest
    ): Result<ListFormResponse> {
        return runCatching {
            service.getListForm(studentId, categoryId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun updateStatusForm(
        formId: String,
        request: UpdateStatusFormRequest
    ): Result<UpdateStatusFormResponse> {
        return runCatching {
            service.updateStatusForm(
                formId = formId,
                request = request
            )
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun uploadForm(
        adminId: String,
        request: UploadFormRequest
    ): Result<BaseCRUDFormResponse> {
        return runCatching {
            service.uploadForm(adminId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun updateForm(
        formId: String,
        request: BaseCreateFormRequest
    ): Result<BaseCRUDFormResponse> {
        return runCatching {
            service.updateForm(formId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun deleteForm(formId: String): Result<BaseCRUDFormResponse> {
        return runCatching {
            service.deleteForm(formId)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun getStatisticsForm(request: StatisticsRequest): Result<StatisticsResponse> {
        return runCatching {
            service.getStatisticsForm(request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun countForms(): Result<CountFormsResponse> {
        return runCatching {
            service.countForms()
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }
}