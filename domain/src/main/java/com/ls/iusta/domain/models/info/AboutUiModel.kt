package com.ls.iusta.domain.models.info

import com.ls.iusta.domain.models.UiAwareModel

sealed class AboutUiModel : UiAwareModel() {
    object Loading : AboutUiModel()
    data class Error(var error: String = "") : AboutUiModel()
    data class Success(var data: List<About> = emptyList()) : AboutUiModel()
}
