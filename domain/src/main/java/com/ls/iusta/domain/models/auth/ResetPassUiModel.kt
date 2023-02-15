package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.user.Base

sealed class ResetPassUiModel : UiAwareModel() {
    object Loading : ResetPassUiModel()
    data class Error(var error: String = "") : ResetPassUiModel()
    data class Success(var result: Base) : ResetPassUiModel()
    object EmptyEmail : ResetPassUiModel()
    object IncorrectEmail : ResetPassUiModel()
}
