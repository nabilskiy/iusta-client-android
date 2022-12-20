package com.ls.iusta.domain.models.info

import com.ls.iusta.domain.models.UiAwareModel

sealed class TermsUiModel : UiAwareModel() {
    object Loading : TermsUiModel()
    data class Error(var error: String = "") : TermsUiModel()
    data class Success(var data: List<Terms> = emptyList()) : TermsUiModel()
}
