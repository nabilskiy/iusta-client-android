package com.ls.iusta.domain.models.settings

import com.ls.iusta.domain.models.UiAwareModel

sealed class SettingUiModel : UiAwareModel() {
    object Loading : SettingUiModel()
    data class Error(var error: String = "") : SettingUiModel()
    data class Success(var data: List<Settings> = emptyList()) : SettingUiModel()
    data class NightMode(val nightMode: Boolean) : SettingUiModel()
}
