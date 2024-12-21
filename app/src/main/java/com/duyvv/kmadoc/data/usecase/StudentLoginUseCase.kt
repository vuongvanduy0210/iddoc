package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.StudentLoginRequest
import com.duyvv.kmadoc.data.dto.response.LoginResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StudentLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<StudentLoginRequest, Result<LoginResponse>> {
    override fun execute(value: StudentLoginRequest?): Flow<Result<LoginResponse>> =
        flowFromSuspend {
            authRepository.studentLogin(value!!)
        }.flowOn(Dispatchers.IO)
}