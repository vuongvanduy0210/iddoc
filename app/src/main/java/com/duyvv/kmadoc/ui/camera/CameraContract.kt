package com.duyvv.kmadoc.ui.camera

import com.duyvv.kmadoc.base.mvi.MviEffect
import com.duyvv.kmadoc.base.mvi.MviState
import com.duyvv.kmadoc.data.model.FormModel

class CameraContract {

    sealed class CameraState : MviState {
        data object ConvertImageBitmapSuccess : CameraState()
    }

    sealed class CameraEffect : MviEffect {
        data class OcrImageSuccess(val formModel: FormModel) : CameraEffect()
    }
}