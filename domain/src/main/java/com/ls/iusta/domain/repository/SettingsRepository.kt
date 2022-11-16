package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>>
}