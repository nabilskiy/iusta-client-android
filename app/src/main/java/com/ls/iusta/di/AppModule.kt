package com.ls.iusta.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ls.iusta.R
import com.ls.iusta.core.theme.LocaleUtils
import com.ls.iusta.core.theme.LocaleUtilsImpl
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.core.theme.ThemeUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils = themeUtilsImpl

    @Singleton
    @Provides
    fun provideLocaleUtils(localeUtilsImpl: LocaleUtilsImpl): LocaleUtils = localeUtilsImpl

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
}