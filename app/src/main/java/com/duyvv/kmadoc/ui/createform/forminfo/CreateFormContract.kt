package com.duyvv.kmadoc.ui.createform.forminfo

import com.duyvv.kmadoc.base.mvi.MviState

class CreateFormContract {

    sealed class FormInfoState : MviState {
        data object CreateFormSuccess : FormInfoState()
    }
}