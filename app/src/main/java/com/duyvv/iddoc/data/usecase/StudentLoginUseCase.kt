package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.request.StudentLoginRequest
import com.duyvv.iddoc.data.dto.response.LoginResponse
import com.duyvv.iddoc.data.repository.AuthRepository
import com.duyvv.iddoc.util.flowFromSuspend
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