package com.duyvv.kmadoc.ui.auth

import com.duyvv.kmadoc.base.mvi.MviEffect
import com.duyvv.kmadoc.data.dto.response.LoginResponse

class AuthContract {
    sealed class AuthEffect : MviEffect {
        data class LoginSuccess(val response: LoginResponse) : AuthEffect()
    }
}