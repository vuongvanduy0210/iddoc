package com.duyvv.kmadoc.data.remote.service

import com.duyvv.kmadoc.data.dto.request.AdminLoginRequest
import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.request.ChangeAdminInfoRequest
import com.duyvv.kmadoc.data.dto.request.ChangePassRequest
import com.duyvv.kmadoc.data.dto.request.ChangeStudentInfoRequest
import com.duyvv.kmadoc.data.dto.request.GetListFormRequest
import com.duyvv.kmadoc.data.dto.request.SignUpRequest
import com.duyvv.kmadoc.data.dto.request.StatisticsRequest
import com.duyvv.kmadoc.data.dto.request.StudentLoginRequest
import com.duyvv.kmadoc.data.dto.request.UpdateStatusFormRequest
import com.duyvv.kmadoc.data.dto.request.UploadFormRequest
import com.duyvv.kmadoc.data.dto.response.AdminInfoResponse
import com.duyvv.kmadoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.kmadoc.data.dto.response.CountFormsResponse
import com.duyvv.kmadoc.data.dto.response.CreateFormResponse
import com.duyvv.kmadoc.data.dto.response.FormTypesResponse
import com.duyvv.kmadoc.data.dto.response.ListFormResponse
import com.duyvv.kmadoc.data.dto.response.LoginResponse
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.dto.response.StatisticsResponse
import com.duyvv.kmadoc.data.dto.response.StudentInfoResponse
import com.duyvv.kmadoc.data.dto.response.UpdateStatusFormResponse
import com.duyvv.kmadoc.util.SharePreferenceExt
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
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

    suspend fun changeStudentInfo(
        studentId: String,
        request: ChangeStudentInfoRequest
    ): SignUpResponse {
        return client.put("v1/students/$studentId") {
            setBody(request)
        }.body()
    }

    suspend fun changeAdminInfo(adminId: String, request: ChangeAdminInfoRequest): SignUpResponse {
        return client.put("v1/admins/$adminId") {
            setBody(request)
        }.body()
    }

    suspend fun getStudentInfo(studentId: String): StudentInfoResponse {
        return client.get("v1/students/$studentId").body()
    }

    suspend fun getAdminInfo(adminId: String): AdminInfoResponse {
        return client.get("v1/admins/$adminId").body()
    }

    suspend fun changePassword(userId: String, request: ChangePassRequest): SignUpResponse {
        val url = if (SharePreferenceExt.isAdminAccount) {
            "v1/admins/$userId"
        } else {
            "v1/students/$userId"
        }
        return client.put(url) {
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

    suspend fun getStatisticsForm(request: StatisticsRequest): StatisticsResponse {
        return client.post("v1/forms/statistic/group-by-category") {
            setBody(request)
        }.body()
    }

    suspend fun countForms(): CountFormsResponse {
        return client.get("v1/forms/statistic/count").body()
    }
}