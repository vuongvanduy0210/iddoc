package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.BaseCreateFormRequest
import com.duyvv.kmadoc.data.dto.response.CreateFormResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateFormUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<BaseCreateFormRequest, Result<CreateFormResponse>> {
    override fun execute(value: BaseCreateFormRequest?): Flow<Result<CreateFormResponse>> =
        flowFromSuspend {
            repo.createForm(body = value!!)
        }.flowOn(Dispatchers.IO)

    fun execute(
        studentId: String,
        formId: String,
        body: BaseCreateFormRequest
    ): Flow<Result<CreateFormResponse>> = flowFromSuspend {
        repo.createForm(studentId, formId, body)
    }.flowOn(Dispatchers.IO)
}