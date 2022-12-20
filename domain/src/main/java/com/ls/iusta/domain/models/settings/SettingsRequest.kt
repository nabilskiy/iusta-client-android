package com.ls.iusta.domain.models.settings

data class SettingsRequest(
    val isNightMode: Boolean,
    var type: String
)
