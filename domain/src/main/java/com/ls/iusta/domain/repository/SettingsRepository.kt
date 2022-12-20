package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean, type: String): Flow<List<Settings>>
}