package com.ls.iusta.data

import com.ls.iusta.domain.models.SettingType
import com.ls.iusta.domain.models.Settings
import com.ls.iusta.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val appVersion: String) :
    SettingsRepository {
    override suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>> = flow {
        emit(getData(isNightMode))
    }

    // This should be came from api but we don't have api so we are creating locally
    private fun getData(isNightMode: Boolean): List<Settings> {
        val settingList = mutableListOf<Settings>()
        settingList.add(Settings(1, SettingType.SWITCH, "Theme mode", "", isNightMode))
        settingList.add(Settings(2, SettingType.EMPTY, "Clear cache", ""))
        settingList.add(Settings(2, SettingType.TEXT, "App version", appVersion))
        return settingList
    }
}