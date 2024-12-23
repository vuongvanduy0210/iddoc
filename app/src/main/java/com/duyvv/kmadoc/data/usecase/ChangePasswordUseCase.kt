package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.ChangePassRequest
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repo: AuthRepository
) : UseCase<ChangePassRequest, Result<SignUpResponse>> {
    override fun execute(value: ChangePassRequest?): Flow<Result<SignUpResponse>> {
        TODO("Not yet implemented")
    }

    fun execute(userId: String, value: ChangePassRequest?): Flow<Result<SignUpResponse>> =
        flowFromSuspend {
            repo.changePass(userId, value!!)
        }.flowOn(Dispatchers.IO)
}