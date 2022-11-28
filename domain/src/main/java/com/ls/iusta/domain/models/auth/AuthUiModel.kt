package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel

sealed class AuthUiModel : UiAwareModel() {
    object Loading : AuthUiModel()
    data class Error(var error: String = "") : AuthUiModel()
    data class Success(var isLogged: Boolean?) : AuthUiModel()
}
