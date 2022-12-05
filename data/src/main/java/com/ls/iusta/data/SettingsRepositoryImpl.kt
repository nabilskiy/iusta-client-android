package com.ls.iusta.data

import com.ls.iusta.domain.models.settings.SettingType
import com.ls.iusta.domain.models.settings.Settings
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
        settingList.add(Settings(2, SettingType.EMPTY, "Settings", ""))
        settingList.add(Settings(3, SettingType.EMPTY, "Change password", ""))
        settingList.add(Settings(4, SettingType.EMPTY, "Contact us", ""))
        settingList.add(Settings(5, SettingType.EMPTY, "About", ""))
        settingList.add(Settings(6, SettingType.EMPTY, "Logout", ""))
        settingList.add(Settings(7, SettingType.TEXT, "App version", appVersion))
        return settingList
    }
}