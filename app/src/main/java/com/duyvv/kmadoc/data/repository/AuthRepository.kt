package com.duyvv.kmadoc.data.repository

import com.duyvv.kmadoc.data.dto.request.AdminLoginRequest
import com.duyvv.kmadoc.data.dto.request.ChangeAdminInfoRequest
import com.duyvv.kmadoc.data.dto.request.ChangePassRequest
import com.duyvv.kmadoc.data.dto.request.ChangeStudentInfoRequest
import com.duyvv.kmadoc.data.dto.request.SignUpRequest
import com.duyvv.kmadoc.data.dto.request.StudentLoginRequest
import com.duyvv.kmadoc.data.dto.response.AdminInfoResponse
import com.duyvv.kmadoc.data.dto.response.LoginResponse
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.dto.response.StudentInfoResponse
import com.duyvv.kmadoc.data.remote.service.FormManagementService
import com.duyvv.kmadoc.util.DEFAULT_ERROR_MESSAGE
import javax.inject.Inject

interface AuthRepository {

    suspend fun studentLogin(request: StudentLoginRequest): Result<LoginResponse>

    suspend fun adminLogin(request: AdminLoginRequest): Result<LoginResponse>

    suspend fun studentSignUp(request: SignUpRequest): Result<SignUpResponse>

    suspend fun changeStudentInfo(
        studentId: String,
        request: ChangeStudentInfoRequest
    ): Result<SignUpResponse>

    suspend fun changeAdminInfo(
        studentId: String,
        request: ChangeAdminInfoRequest
    ): Result<SignUpResponse>

    suspend fun getStudentInfo(studentId: String): Result<StudentInfoResponse>

    suspend fun getAdminInfo(adminId: String): Result<AdminInfoResponse>

    suspend fun changePass(userId: String, request: ChangePassRequest): Result<SignUpResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val managementService: FormManagementService
) : AuthRepository {
    override suspend fun studentLogin(request: StudentLoginRequest): Result<LoginResponse> {
        return runCatching<AuthRepositoryImpl, LoginResponse> {
            managementService.studentLogin(request)
        }.mapCatching { response ->
            if (response.message == "Successfull") {
                response
            } else {
                throw Exception(response.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun adminLogin(request: AdminLoginRequest): Result<LoginResponse> {
        return runCatching<AuthRepositoryImpl, LoginResponse> {
            managementService.adminLogin(request)
        }.mapCatching { response ->
            if (response.message == "Successfull") {
                response
            } else {
                throw Exception(response.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun studentSignUp(request: SignUpRequest): Result<SignUpResponse> {
        return runCatching {
            managementService.studentSignup(request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun changeStudentInfo(
        studentId: String,
        request: ChangeStudentInfoRequest
    ): Result<SignUpResponse> {
        return runCatching {
            managementService.changeStudentInfo(studentId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun changeAdminInfo(
        adminId: String,
        request: ChangeAdminInfoRequest
    ): Result<SignUpResponse> {
        return runCatching {
            managementService.changeAdminInfo(adminId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun getStudentInfo(studentId: String): Result<StudentInfoResponse> {
        return runCatching {
            managementService.getStudentInfo(studentId)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun getAdminInfo(adminId: String): Result<AdminInfoResponse> {
        return runCatching {
            managementService.getAdminInfo(adminId)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    override suspend fun changePass(
        userId: String,
        request: ChangePassRequest
    ): Result<SignUpResponse> {
        return runCatching {
            managementService.changePassword(userId, request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }
}