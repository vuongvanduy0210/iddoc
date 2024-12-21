package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteFormUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<String, Result<BaseCRUDFormResponse>> {
    override fun execute(value: String?): Flow<Result<BaseCRUDFormResponse>> = flowFromSuspend {
        repo.deleteForm(value!!)
    }.flowOn(Dispatchers.IO)
}