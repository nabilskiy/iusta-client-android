package com.ls.iusta

import android.app.Application
import android.content.Context
import com.ls.iusta.core.locale.LocaleUtils
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class IUSTAApp : Application() {

    @Inject
    lateinit var themeUtils: ThemeUtils

    @Inject
    lateinit var localeUtils: LocaleUtils

    @Inject
    lateinit var preferencesHelper: PresentationPreferencesHelper

    override fun onCreate() {
        super.onCreate()
        initNightMode()
     //   setLocale()
    }

    /**
     * Initialize Night Mode based on user last saved state (day/night themes).
     */
    private fun initNightMode() {
        themeUtils.setNightMode(preferencesHelper.isNightMode)
    }

    private fun setLocale(){
        localeUtils.setLocale(applicationContext, preferencesHelper.locale.toString())
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(localeUtils.setLocale(applicationContext, preferencesHelper.locale.toString()))
//    }
//
}