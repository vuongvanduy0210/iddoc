package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.OCRFormRequest
import com.duyvv.kmadoc.data.dto.response.OCRFormResponse
import com.duyvv.kmadoc.data.repository.OCRFormRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OCRFormUseCase @Inject constructor(
    private val ocrFormRepository: OCRFormRepository
) : UseCase<OCRFormRequest, Result<OCRFormResponse>> {
    override fun execute(value: OCRFormRequest?): Flow<Result<OCRFormResponse>> = flowFromSuspend {
        ocrFormRepository.ocrForm(value!!)
    }.flowOn(Dispatchers.IO)
}