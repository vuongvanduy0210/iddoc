package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.request.AdminLoginRequest
import com.duyvv.iddoc.data.dto.response.LoginResponse
import com.duyvv.iddoc.data.repository.AuthRepository
import com.duyvv.iddoc.util.flowFromSuspend
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