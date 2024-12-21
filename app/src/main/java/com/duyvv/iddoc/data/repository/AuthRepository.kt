package com.duyvv.iddoc.data.repository

import com.duyvv.iddoc.data.dto.request.AdminLoginRequest
import com.duyvv.iddoc.data.dto.request.SignUpRequest
import com.duyvv.iddoc.data.dto.request.StudentLoginRequest
import com.duyvv.iddoc.data.dto.response.LoginResponse
import com.duyvv.iddoc.data.dto.response.SignUpResponse
import com.duyvv.iddoc.data.remote.service.FormManagementService
import com.duyvv.iddoc.util.DEFAULT_ERROR_MESSAGE
import javax.inject.Inject

interface AuthRepository {

    suspend fun studentLogin(request: StudentLoginRequest): Result<LoginResponse>

    suspend fun adminLogin(request: AdminLoginRequest): Result<LoginResponse>

    suspend fun studentSignUp(request: SignUpRequest): Result<SignUpResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val smartOCRService: FormManagementService
) : AuthRepository {
    override suspend fun studentLogin(request: StudentLoginRequest): Result<LoginResponse> {
        return runCatching<AuthRepositoryImpl, LoginResponse> {
            smartOCRService.studentLogin(request)
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
            smartOCRService.adminLogin(request)
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
            smartOCRService.studentSignup(request)
        }.mapCatching {
            if (it.message == "Successfull") {
                it
            } else {
                throw Exception(it.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }
}