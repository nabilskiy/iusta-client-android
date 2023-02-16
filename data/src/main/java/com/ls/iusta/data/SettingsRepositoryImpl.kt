package com.ls.iusta.data

import com.ls.iusta.domain.models.settings.SettingType
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val appVersion: String) :
    SettingsRepository {
    override suspend fun getSettings(
        isNightMode: Boolean,
        type: String,
        lang: String
    ): Flow<List<Settings>> = flow {
        when (type) {
            "main" -> {
                emit(getData(isNightMode, lang))
            }
            "about" -> {
                emit(getAboutData(lang))
            }
        }

    }

    private fun getData(isNightMode: Boolean, lang: String): List<Settings> {
        val settingList = mutableListOf<Settings>()
        when (lang) {
            "az" -> {
                settingList.add(Settings(1, SettingType.SWITCH, "Mövzu", "", isNightMode))
                settingList.add(Settings(2, SettingType.EMPTY, "Profilə düzəliş et", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Parolu dəyişdirin", ""))
                settingList.add(Settings(4, SettingType.EMPTY, "Haqqında", ""))
                settingList.add(Settings(5, SettingType.EMPTY, "Bizimlə əlaqə saxlayın", ""))
                settingList.add(Settings(6, SettingType.EMPTY, "Çıxış", ""))
                settingList.add(Settings(7, SettingType.TEXT, "Proqram versiyası", appVersion))
            }
            "en" -> {
                settingList.add(Settings(1, SettingType.SWITCH, "Theme", "", isNightMode))
                settingList.add(Settings(2, SettingType.EMPTY, "Edit profile", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Change password", ""))
                settingList.add(Settings(4, SettingType.EMPTY, "About", ""))
                settingList.add(Settings(5, SettingType.EMPTY, "Contact Us", ""))
                settingList.add(Settings(6, SettingType.EMPTY, "Logout", ""))
                settingList.add(Settings(7, SettingType.TEXT, "App version", appVersion))
            }
            "ru" -> {
                settingList.add(Settings(1, SettingType.SWITCH, "Тема", "", isNightMode))
                settingList.add(Settings(2, SettingType.EMPTY, "Редактировать профиль", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Изменить пароль", ""))
                settingList.add(Settings(4, SettingType.EMPTY, "О нас", ""))
                settingList.add(Settings(5, SettingType.EMPTY, "Контакты", ""))
                settingList.add(Settings(6, SettingType.EMPTY, "Выйти", ""))
                settingList.add(Settings(7, SettingType.TEXT, "Версия", appVersion))
            }
            else -> {
                settingList.add(Settings(1, SettingType.SWITCH, "Theme", "", isNightMode))
                settingList.add(Settings(2, SettingType.EMPTY, "Edit profile", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Change password", ""))
                settingList.add(Settings(4, SettingType.EMPTY, "About", ""))
                settingList.add(Settings(5, SettingType.EMPTY, "Contact Us", ""))
                settingList.add(Settings(6, SettingType.EMPTY, "Logout", ""))
                settingList.add(Settings(7, SettingType.TEXT, "App version", appVersion))
            }
        }

        return settingList
    }

    private fun getAboutData(lang: String): List<Settings> {
        val settingList = mutableListOf<Settings>()
        when (lang) {
            "az" -> {
                settingList.add(Settings(1, SettingType.EMPTY, "FAQ", ""))
                settingList.add(Settings(2, SettingType.EMPTY, "Gizlilik Siyasəti", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Şərtlər və Qaydalar", ""))
            }
            "en" -> {
                settingList.add(Settings(1, SettingType.EMPTY, "FAQ", ""))
                settingList.add(Settings(2, SettingType.EMPTY, "Privacy Policy", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Terms and Conditions", ""))
            }
            "ru" -> {
                settingList.add(Settings(1, SettingType.EMPTY, "ЧаВо", ""))
                settingList.add(Settings(2, SettingType.EMPTY, "Политика конфиденциальности", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Условия и положения", ""))
            }
            else ->{
                settingList.add(Settings(1, SettingType.EMPTY, "FAQ", ""))
                settingList.add(Settings(2, SettingType.EMPTY, "Privacy Policy", ""))
                settingList.add(Settings(3, SettingType.EMPTY, "Terms and Conditions", ""))
            }
        }
        return settingList
    }
}