package com.duyvv.iddoc.ui.createform.forminfo

import com.duyvv.iddoc.base.mvi.MviState

class CreateFormContract {

    sealed class FormInfoState : MviState {
        data object CreateFormSuccess : FormInfoState()
    }
}