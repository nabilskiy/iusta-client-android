package com.ls.iusta.domain.models.user

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login

sealed class UserUiModel : UiAwareModel() {
    object Loading : UserUiModel()
    data class Error(var error: String = "") : UserUiModel()
    data class Success(var data: User) : UserUiModel()
}
