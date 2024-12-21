package com.duyvv.iddoc.ui.demoinfo

import com.duyvv.iddoc.base.mvi.MviEffect

class DemoInfoContract {

    sealed class DemoInfoEffect : MviEffect {
        data object UpdateStatusFormSuccess : DemoInfoEffect()
        data object UploadFormSuccess : DemoInfoEffect()
        data object UpdateFormSuccess : DemoInfoEffect()
        data object DeleteFormSuccess : DemoInfoEffect()
    }
}