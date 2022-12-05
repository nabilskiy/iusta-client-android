package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel

sealed class RegUiModel : UiAwareModel() {
    object Loading : RegUiModel()
    data class Error(var error: String = "") : RegUiModel()
    data class Success(var result: Boolean) : RegUiModel()
}
