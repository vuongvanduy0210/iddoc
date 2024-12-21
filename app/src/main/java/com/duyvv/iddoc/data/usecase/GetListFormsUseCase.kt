package com.duyvv.iddoc.data.usecase

import com.duyvv.iddoc.base.UseCase
import com.duyvv.iddoc.data.dto.request.GetListFormRequest
import com.duyvv.iddoc.data.dto.response.ListFormResponse
import com.duyvv.iddoc.data.repository.FormManagementRepository
import com.duyvv.iddoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListFormsUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<GetListFormRequest, Result<ListFormResponse>> {
    override fun execute(value: GetListFormRequest?): Flow<Result<ListFormResponse>> {
        TODO("Not yet implemented")
    }

    fun execute(
        studentId: String?,
        categoryId: String?,
        request: GetListFormRequest
    ): Flow<Result<ListFormResponse>> = flowFromSuspend {
        repo.getListForm(studentId, categoryId, request)
    }.flowOn(Dispatchers.IO)
}