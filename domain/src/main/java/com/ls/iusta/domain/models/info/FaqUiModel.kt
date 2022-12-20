package com.ls.iusta.domain.models.info

import com.ls.iusta.domain.models.UiAwareModel

sealed class FaqUiModel : UiAwareModel() {
    object Loading : FaqUiModel()
    data class Error(var error: String = "") : FaqUiModel()
    data class Success(var data: List<Faq> = emptyList()) : FaqUiModel()
}
