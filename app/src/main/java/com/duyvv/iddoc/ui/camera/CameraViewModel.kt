package com.duyvv.iddoc.ui.camera

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.data.dto.request.OCRFormRequest
import com.duyvv.iddoc.data.model.FormModel
import com.duyvv.iddoc.data.model.FormTypeModel
import com.duyvv.iddoc.data.usecase.OCRFormUseCase
import com.duyvv.iddoc.util.onEachError
import com.duyvv.iddoc.util.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val ocrFormUseCase: OCRFormUseCase
) : BaseViewModel() {

    private val _formModel = MutableStateFlow<FormModel?>(null)
    val formModel = _formModel.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    var formTypeModel: FormTypeModel? = null

    fun initData(imageBitmap: Bitmap?, imageUri: Uri?, formType: FormTypeModel?) {
        _imageUri.value = imageUri
        this.formTypeModel = formType
    }

    fun updateUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun ocr(context: Context, formTypeModel: FormTypeModel) {
        showLoading(true)
        uploadImage(context).onEachError {
            showLoading(false)
            showError(Throwable("Tải ảnh thất bại: ${it.message}"))
        }.onEach {
            Log.d("Image URi", it)
            ocrImage(it, formTypeModel)
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScopeExceptionHandler)
        /*ocrImage(
            "https://res.cloudinary.com/dog8piybp/image/upload/v1734242281/tp2e3zryf6wdfvfs234n.jpg",
            formTypeModel
        )*/
    }

    private fun uploadImage(context: Context) = callbackFlow {
        try {
            val inputStream = context.contentResolver.openInputStream(_imageUri.value!!)
            val byteArray = inputStream?.use { it.readBytes() }
            MediaManager.get().upload(byteArray)
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        // Có thể gửi trạng thái nếu cần
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        // Có thể gửi tiến độ nếu cần
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        val url = resultData["url"] as String
                        trySend(Result.success(url))
                        close()
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        trySend(Result.failure(Exception(error.description)))
                        close()
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                    }
                })
                .dispatch()
        } catch (e: Exception) {
            trySend(Result.failure(e))
            close()
        }

        awaitClose {

        }
    }

    private fun ocrImage(imageUrl: String, formTypeModel: FormTypeModel) {
        ocrFormUseCase.execute(
            OCRFormRequest(
                imageUrls = listOf(imageUrl),
                applicationName = formTypeModel.title
            )
        ).onEachError {
            showLoading(false)
            Log.e("OCR Error", "$it")
            showError(it)
        }.onEach {
            showLoading(false)
            _formModel.value = it.toDomainModel()
            sendEffect(CameraContract.CameraEffect.OcrImageSuccess(it.toDomainModel()))
        }.launchIn(viewModelScopeExceptionHandler)
    }
}
