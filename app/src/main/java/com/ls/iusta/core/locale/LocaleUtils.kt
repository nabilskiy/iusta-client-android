package com.ls.iusta.core.locale

import android.content.Context

interface LocaleUtils {
    fun setLocale(context: Context, locale: String): Context
    fun getLocale(): String
}