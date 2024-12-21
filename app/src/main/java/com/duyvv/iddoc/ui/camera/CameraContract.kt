package com.duyvv.iddoc.ui.camera

import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.base.mvi.MviState
import com.duyvv.iddoc.data.model.FormModel

class CameraContract {

    sealed class CameraState : MviState {
        data object ConvertImageBitmapSuccess : CameraState()
    }

    sealed class CameraEffect : MviEffect {
        data class OcrImageSuccess(val formModel: FormModel) : CameraEffect()
    }
}