package com.ls.iusta.di

import android.content.Context
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.CoroutineContextProviderImp
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    // If in class PresentationPreferencesHelper.kt,
    // I provided @ApplicationContext context: Context params,
    // I did not need to this provider and i could remove this provider
    // this provider just provide context for PresentationPreferencesHelper.kt
    @Singleton
    @Provides
    fun providePresentationPreferenceHelper(
        @ApplicationContext context: Context
    ): PresentationPreferencesHelper = PresentationPreferencesHelper(context)

    @Singleton
    @Provides
    fun provideCoroutineContextProvider(
        coroutineContextProviderImp: CoroutineContextProviderImp
    ): CoroutineContextProvider = coroutineContextProviderImp
}