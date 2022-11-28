package com.ls.iusta.cache.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

open class CachePreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ls.iusta.cache.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
        private const val PREF_KEY_AUTH_TOKEN = "token"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastCacheTime: Long
        get() = preferences.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = preferences.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

    var authToken: String?
        get() = preferences.getString(PREF_KEY_AUTH_TOKEN, "")
        set(authToken) = preferences.edit().putString(PREF_KEY_AUTH_TOKEN, authToken).apply()
}
