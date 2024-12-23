package com.duyvv.kmadoc.ui.changeuserinfo

import com.duyvv.kmadoc.base.mvi.MviEffect

class ChangeInfoContract {
    sealed class ChangeInfoEffect : MviEffect {
        data object ChangeInfoSuccess : ChangeInfoEffect()
        data object GetStudentInfoSuccess : ChangeInfoEffect()
        data object GetAdminInfoSuccess : ChangeInfoEffect()

        data object ChangePasswordSuccess : ChangeInfoEffect()
    }
}