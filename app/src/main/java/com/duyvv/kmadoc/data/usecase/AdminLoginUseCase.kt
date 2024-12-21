package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.AdminLoginRequest
import com.duyvv.kmadoc.data.dto.response.LoginResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdminLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<AdminLoginRequest, Result<LoginResponse>> {
    override fun execute(value: AdminLoginRequest?): Flow<Result<LoginResponse>> = flowFromSuspend {
        authRepository.adminLogin(value!!)
    }.flowOn(Dispatchers.IO)
}