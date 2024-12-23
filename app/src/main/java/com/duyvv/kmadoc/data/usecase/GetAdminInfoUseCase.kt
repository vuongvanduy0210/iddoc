package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.response.AdminInfoResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAdminInfoUseCase @Inject constructor(
    private val repo: AuthRepository
) : UseCase<String, Result<AdminInfoResponse>> {
    override fun execute(value: String?): Flow<Result<AdminInfoResponse>> = flowFromSuspend {
        repo.getAdminInfo(value!!)
    }.flowOn(Dispatchers.IO)
}