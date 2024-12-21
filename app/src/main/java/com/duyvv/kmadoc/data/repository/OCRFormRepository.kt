package com.duyvv.kmadoc.data.repository

import com.duyvv.kmadoc.data.dto.request.OCRFormRequest
import com.duyvv.kmadoc.data.dto.response.OCRFormResponse
import com.duyvv.kmadoc.data.remote.service.OCRService
import com.duyvv.kmadoc.util.DEFAULT_ERROR_MESSAGE
import javax.inject.Inject

interface OCRFormRepository {

    suspend fun ocrForm(request: OCRFormRequest): Result<OCRFormResponse>
}

class OCRFormRepositoryImpl @Inject constructor(
    private val ocrService: OCRService
) : OCRFormRepository {
    override suspend fun ocrForm(request: OCRFormRequest): Result<OCRFormResponse> {
        return runCatching {
            ocrService.ocrForm(request)
        }.mapCatching {
            if (it.extractedText != null) {
                it
            } else {
                throw Exception(DEFAULT_ERROR_MESSAGE)
            }
        }
    }
}