package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.UpdateStatusFormRequest
import com.duyvv.kmadoc.data.dto.response.UpdateStatusFormResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateStatusFormUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<Any?, Result<UpdateStatusFormResponse>> {

    override fun execute(value: Any?): Flow<Result<UpdateStatusFormResponse>> = flowFromSuspend {
        TODO("Not yet implemented")
    }

    fun execute(
        formId: String,
        request: UpdateStatusFormRequest
    ): Flow<Result<UpdateStatusFormResponse>> = flowFromSuspend {
        repo.updateStatusForm(formId, request)
    }.flowOn(Dispatchers.IO)
}