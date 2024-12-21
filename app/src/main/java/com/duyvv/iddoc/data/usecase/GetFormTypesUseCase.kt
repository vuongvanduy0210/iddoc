package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.response.FormTypesResponse
import com.duyvv.iddoc.data.repository.FormManagementRepository
import com.duyvv.iddoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFormTypesUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<Any?, Result<FormTypesResponse>> {
    override fun execute(value: Any?): Flow<Result<FormTypesResponse>> = flowFromSuspend {
        repo.getFormTypes()
    }.flowOn(Dispatchers.IO)
}