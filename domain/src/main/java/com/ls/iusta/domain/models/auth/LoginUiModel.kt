package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel

sealed class LoginUiModel : UiAwareModel() {
    object Loading : LoginUiModel()
    data class Error(var error: String = "") : LoginUiModel()
    object EmptyEmail : LoginUiModel()
    object IncorrectEmail : LoginUiModel()
    object PasswordError: LoginUiModel()
    data class Success(var data: Login) : LoginUiModel()
    data class CheckLogin(var status: Boolean?) : LoginUiModel()

}
