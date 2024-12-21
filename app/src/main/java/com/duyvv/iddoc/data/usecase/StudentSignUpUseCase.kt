package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.request.SignUpRequest
import com.duyvv.iddoc.data.dto.response.SignUpResponse
import com.duyvv.iddoc.data.repository.AuthRepository
import com.duyvv.iddoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StudentSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<SignUpRequest, Result<SignUpResponse>> {
    override fun execute(value: SignUpRequest?): Flow<Result<SignUpResponse>> =
        flowFromSuspend {
            authRepository.studentSignUp(value!!)
        }.flowOn(Dispatchers.IO)
}