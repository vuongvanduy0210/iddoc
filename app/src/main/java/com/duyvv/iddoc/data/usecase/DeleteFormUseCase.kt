package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.iddoc.data.repository.FormManagementRepository
import com.duyvv.iddoc.util.flowFromSuspend
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