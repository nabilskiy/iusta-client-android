package com.ls.iusta.data

import com.ls.iusta.domain.models.settings.SettingType
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val appVersion: String) :
    SettingsRepository {
    override suspend fun getSettings(isNightMode: Boolean, type: String): Flow<List<Settings>> = flow {
       when(type){
            "main" -> {
                emit(getData(isNightMode))
           }
           "about" -> {
               emit(getAboutData())
           }
       }

    }

    private fun getData(isNightMode: Boolean): List<Settings> {
        val settingList = mutableListOf<Settings>()
        settingList.add(Settings(1, SettingType.SWITCH, "Theme", "", isNightMode))
        settingList.add(Settings(2, SettingType.EMPTY, "Edit profile", ""))
        settingList.add(Settings(3, SettingType.EMPTY, "Change password", ""))
        settingList.add(Settings(4, SettingType.EMPTY, "About", ""))
        settingList.add(Settings(5, SettingType.EMPTY, "Contact Us", ""))
        settingList.add(Settings(6, SettingType.EMPTY, "Logout", ""))
        settingList.add(Settings(7, SettingType.TEXT, "App version", appVersion))
        return settingList
    }

    private fun getAboutData(): List<Settings> {
        val settingList = mutableListOf<Settings>()
        settingList.add(Settings(1, SettingType.EMPTY, "FAQ", ""))
        settingList.add(Settings(2, SettingType.EMPTY, "Privacy Policy", ""))
        settingList.add(Settings(3, SettingType.EMPTY, "Terms and Conditions", ""))
        return settingList
    }
}