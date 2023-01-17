package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel

sealed class ResetPassUiModel : UiAwareModel() {
    object Loading : ResetPassUiModel()
    data class Error(var error: String = "") : ResetPassUiModel()
    data class Success(var result: Boolean) : ResetPassUiModel()
    object EmptyEmail : ResetPassUiModel()
    object IncorrectEmail : ResetPassUiModel()
}
