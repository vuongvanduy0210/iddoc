package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateFormUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<BaseCreateFormRequest, Result<BaseCRUDFormResponse>> {

    override fun execute(value: BaseCreateFormRequest?): Flow<Result<BaseCRUDFormResponse>> {
        TODO("Not yet implemented")
    }

    fun execute(
        formId: String,
        request: BaseCreateFormRequest
    ): Flow<Result<BaseCRUDFormResponse>> = flowFromSuspend {
        repo.updateForm(formId, request)
    }.flowOn(Dispatchers.IO)
}