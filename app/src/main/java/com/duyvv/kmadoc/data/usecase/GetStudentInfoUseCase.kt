package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.response.StudentInfoResponse
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStudentInfoUseCase @Inject constructor(
    private val repo: AuthRepository
) : UseCase<String, Result<StudentInfoResponse>> {

    override fun execute(value: String?): Flow<Result<StudentInfoResponse>> = flowFromSuspend {
        repo.getStudentInfo(value!!)
    }.flowOn(Dispatchers.IO)
}