package com.ls.iusta.domain.models.user

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login

sealed class ChangePassUiModel : UiAwareModel() {
    object Loading : ChangePassUiModel()
    data class Error(var error: String = "") : ChangePassUiModel()
    data class Success(var result: Base) : ChangePassUiModel()
}
