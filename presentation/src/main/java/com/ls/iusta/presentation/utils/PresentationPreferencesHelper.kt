package com.ls.iusta.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

class PresentationPreferencesHelper @Inject constructor (context: Context) {
    companion object {
        private const val PREF_PACKAGE_NAME = "com.ls.iusta.presentation.preferences"
        private const val PREF_KEY_NIGHT_MODE = "night_mode"
        private const val PREF_KEY_LOCALE= "locale"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var isNightMode: Boolean
        get() = preferences.getBoolean(PREF_KEY_NIGHT_MODE, false)
        set(isDarkMode) = preferences.edit().putBoolean(PREF_KEY_NIGHT_MODE, isDarkMode).apply()


    var locale: String?
        get() = preferences.getString(PREF_KEY_LOCALE, Locale.getDefault().language.toString())
        set(loc) = preferences.edit().putString(PREF_KEY_LOCALE, loc).apply()
}