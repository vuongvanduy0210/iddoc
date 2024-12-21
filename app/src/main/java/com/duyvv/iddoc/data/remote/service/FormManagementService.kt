package com.duyvv.iddoc.data.remote.service

import com.duyvv.iddoc.data.dto.request.AdminLoginRequest
import com.duyvv.iddoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.iddoc.data.dto.request.GetListFormRequest
import com.duyvv.iddoc.data.dto.request.SignUpRequest
import com.duyvv.iddoc.data.dto.request.StudentLoginRequest
import com.duyvv.iddoc.data.dto.request.UpdateStatusFormRequest
import com.duyvv.iddoc.data.dto.request.UploadFormRequest
import com.duyvv.iddoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.iddoc.data.dto.response.CreateFormResponse
import com.duyvv.iddoc.data.dto.response.FormTypesResponse
import com.duyvv.iddoc.data.dto.response.ListFormResponse
import com.duyvv.iddoc.data.dto.response.LoginResponse
import com.duyvv.iddoc.data.dto.response.SignUpResponse
import com.duyvv.iddoc.data.dto.response.UpdateStatusFormResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class FormManagementService @Inject constructor(private val client: HttpClient) {

    suspend fun adminLogin(request: AdminLoginRequest): LoginResponse {
        return client.post("v1/admins/login") {
            setBody(request)
        }.body()
    }

    suspend fun studentSignup(request: SignUpRequest): SignUpResponse {
        return client.post("v1/students/signup") {
            setBody(request)
        }.body()
    }

    suspend fun studentLogin(request: StudentLoginRequest): LoginResponse {
        return client.post("v1/students/login") {
            setBody(request)
        }.body()
    }

    suspend fun createForm(
        studentId: String,
        categoryId: String,
        request: BaseCreateFormRequest
    ): CreateFormResponse {
        return client.post("v1/forms/createForm") {
            url {
                parameters.append("studentId", studentId)
                parameters.append("categoryId", categoryId)
            }
            setBody(request)
        }.body()
    }

    suspend fun getFormTypes(): FormTypesResponse {
        return client.get("v1/categories").body()
    }

    suspend fun getListForm(
        studentId: String?,
        categoryId: String?,
        request: GetListFormRequest
    ): ListFormResponse {
        return client.post("v1/forms") {
            url {
                if (!studentId.isNullOrEmpty()) {
                    parameters.append("studentId", studentId)
                }
                if (!categoryId.isNullOrEmpty()) {
                    parameters.append("categoryId", categoryId)
                }
            }
            setBody(request)
        }.body()
    }

    suspend fun updateStatusForm(
        formId: String,
        request: UpdateStatusFormRequest
    ): UpdateStatusFormResponse {
        return client.patch("v1/forms/$formId") {
            setBody(request)
        }.body()
    }

    suspend fun uploadForm(
        adminId: String,
        request: UploadFormRequest
    ): BaseCRUDFormResponse {
        return client.post("v1/forms/uploadForm") {
            url {
                parameters.append("adminId", adminId)
            }
            setBody(request)
        }.body()
    }

    suspend fun updateForm(
        formId: String,
        request: BaseCreateFormRequest
    ): BaseCRUDFormResponse {
        return client.post("v1/forms/$formId") {
            setBody(request)
        }.body()
    }

    suspend fun deleteForm(formId: String): BaseCRUDFormResponse {
        return client.delete("v1/forms/$formId").body()
    }
}