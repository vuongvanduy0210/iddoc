package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.ChangeStudentInfoRequest
import com.duyvv.kmadoc.data.dto.response.SignUpResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangeStudentInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCase<ChangeStudentInfoRequest, Result<SignUpResponse>> {

    override fun execute(value: ChangeStudentInfoRequest?): Flow<Result<SignUpResponse>> {
        TODO("Not yet implemented")
    }

    fun execute(studentId: String, value: ChangeStudentInfoRequest?): Flow<Result<SignUpResponse>> =
        flowFromSuspend {
            repository.changeStudentInfo(studentId, value!!)
        }.flowOn(Dispatchers.IO)
}