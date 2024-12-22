package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.response.CountFormsResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CountFormsUseCase @Inject constructor(
    private val repository: FormManagementRepository
) : UseCase<Any, Result<CountFormsResponse>> {

    override fun execute(value: Any?): Flow<Result<CountFormsResponse>> = flowFromSuspend {
        repository.countForms()
    }.flowOn(Dispatchers.IO)
}