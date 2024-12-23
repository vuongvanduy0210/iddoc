package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.ChangeAdminInfoRequest
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangeAdminInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<ChangeAdminInfoRequest, Result<SignUpResponse>> {

    override fun execute(value: ChangeAdminInfoRequest?): Flow<Result<SignUpResponse>> {
        TODO("Not yet implemented")
    }

    fun execute(adminId: String, value: ChangeAdminInfoRequest?): Flow<Result<SignUpResponse>> =
        flowFromSuspend {
            repository.changeAdminInfo(adminId, value!!)
        }.flowOn(Dispatchers.IO)
}