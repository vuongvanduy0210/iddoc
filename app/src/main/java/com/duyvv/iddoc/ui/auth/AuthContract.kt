package com.duyvv.iddoc.ui.auth

import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.data.dto.response.LoginResponse

class AuthContract {
    sealed class AuthEffect : MviEffect {
        data class LoginSuccess(val response: LoginResponse) : AuthEffect()
    }
}