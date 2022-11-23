package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel

sealed class LoginUiModel : UiAwareModel() {
    object Loading : LoginUiModel()
    data class Error(var error: String = "") : LoginUiModel()
    data class Success(var data: Login) : LoginUiModel()
}
