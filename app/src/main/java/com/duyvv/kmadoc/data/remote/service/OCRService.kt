package com.duyvv.kmadoc.data.remote.service

import com.duyvv.kmadoc.data.dto.request.OCRFormRequest
import com.duyvv.kmadoc.data.dto.response.OCRFormResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class OCRService @Inject constructor(private val client: HttpClient) {
    suspend fun ocrForm(request: OCRFormRequest): OCRFormResponse {
        return client.post("api/v1/") {
            setBody(request)
        }.body()
    }
}