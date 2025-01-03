package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.UploadFormRequest
import com.duyvv.kmadoc.data.dto.response.BaseCRUDFormResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UploadFormUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<UploadFormRequest?, Result<BaseCRUDFormResponse>> {

    override fun execute(value: UploadFormRequest?): Flow<Result<BaseCRUDFormResponse>> =
        flowFromSuspend {
            TODO("Not yet implemented")
        }

    fun execute(
        adminId: String,
        request: UploadFormRequest
    ): Flow<Result<BaseCRUDFormResponse>> = flowFromSuspend {
        repo.uploadForm(adminId, request)
    }.flowOn(Dispatchers.IO)
}