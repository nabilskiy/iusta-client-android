package com.ls.iusta

import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class IUSTAApp : LocaleAwareApplication() {

    @Inject
    lateinit var themeUtils: ThemeUtils


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

}