package com.ls.iusta

import android.app.Application
import com.ls.iusta.core.theme.LocaleUtils
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
    }

    /**
     * Initialize Night Mode based on user last saved state (day/night themes).
     */
    private fun initNightMode() {
        themeUtils.setNightMode(preferencesHelper.isNightMode)
    }

    private fun setLocale(){
        localeUtils.setLocale(preferencesHelper.locale)
    }
    
}